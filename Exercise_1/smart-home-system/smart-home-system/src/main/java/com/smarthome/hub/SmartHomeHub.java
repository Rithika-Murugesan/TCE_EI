
package com.smarthome.hub;

import com.smarthome.model.Device;
import com.smarthome.factory.DeviceFactory;
import com.smarthome.observer.Event;
import com.smarthome.observer.Subject;
import com.smarthome.observer.Observer;
import com.smarthome.proxy.DeviceProxy;
import com.smarthome.exceptions.DeviceNotFoundException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Logger;
import com.smarthome.util.LoggerUtil;

/**
 * Singleton Hub: central controller. Implements Subject for Observer pattern.
 */
public class SmartHomeHub implements Subject {
    private static final SmartHomeHub INSTANCE = new SmartHomeHub();
    private final Logger log = LoggerUtil.getLogger(SmartHomeHub.class.getName());

    private final Map<Integer, Device> deviceRegistry = new ConcurrentHashMap<>();
    private final List<Observer> observers = Collections.synchronizedList(new ArrayList<>());
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
    public final DeviceFactory factory = new DeviceFactory();
    private final List<ScheduledFuture<?>> scheduledTasks = Collections.synchronizedList(new ArrayList<>());

    private final List<AutomationTrigger> triggers = Collections.synchronizedList(new ArrayList<>());

    private SmartHomeHub() {}

    public static SmartHomeHub getInstance() { return INSTANCE; }

    public void registerDevice(Device d) {
        DeviceProxy proxy = new DeviceProxy(d);
        deviceRegistry.put(d.getId(), proxy);
        register((Observer)proxy);
        log.info("Registered device " + d.getId() + " type=" + d.getType());
    }

    public void unregisterDevice(int id) {
        Device removed = deviceRegistry.remove(id);
        if (removed != null) {
            unregister((Observer)removed);
            log.info("Unregistered device " + id);
        }
    }

    public Collection<Device> allDevices() { return deviceRegistry.values(); }

    public Optional<Device> getDevice(int id) {
        return Optional.ofNullable(deviceRegistry.get(id));
    }

    // Subject implementation
    @Override public void register(Observer o) { observers.add(o); }
    @Override public void unregister(Observer o) { observers.remove(o); }
    @Override public void notifyObservers(Event event) {
        // notify asynchronously to avoid blocking
        for (Observer o : observers) {
            scheduler.submit(() -> {
                try { o.update(event); } catch (Exception e) { log.warning("Observer update failed: " + e.getMessage()); }
            });
        }
    }

    // Scheduling a textual command at HH:mm today/tomorrow nearest
    public void scheduleCommand(String timeHHmm, Runnable command) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.parse(timeHHmm, fmt);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime target = now.withHour(time.getHour()).withMinute(time.getMinute()).withSecond(0).withNano(0);
        if (target.isBefore(now)) target = target.plusDays(1);
        long delay = java.time.Duration.between(now, target).toMillis();
        log.info("Scheduling command at " + target.toString());
        ScheduledFuture<?> future = scheduler.schedule(() -> {
            try { command.run(); } catch (Exception e) { log.warning("Scheduled command failed: " + e.getMessage()); }
        }, delay, TimeUnit.MILLISECONDS);
        scheduledTasks.add(future);
    }

    public void addTrigger(AutomationTrigger t) {
        triggers.add(t);
        log.info("Added trigger: " + t.toString());
    }

    public void evaluateTriggers() {
        // Example evaluation runs periodically or upon events; here we simply check temp triggers
        for (AutomationTrigger t : new ArrayList<>(triggers)) {
            if (t.evaluate(this)) {
                log.info("Trigger matched: " + t.toString());
                // execute action
                try {
                    t.executeAction(this);
                } catch (Exception e) {
                    log.warning("Failed to execute trigger action: " + e.getMessage());
                }
            }
        }
    }

    public void broadcastEvent(String type, Object payload) {
        notifyObservers(new Event(type, payload));
        evaluateTriggers();
    }

    public void shutdown() {
        log.info("Hub shutting down. Cancelling schedules.");
        for (ScheduledFuture<?> f : scheduledTasks) f.cancel(true);
        scheduler.shutdownNow();
    }
}
