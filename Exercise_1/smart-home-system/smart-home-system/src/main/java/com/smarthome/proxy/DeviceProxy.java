
package com.smarthome.proxy;

import com.smarthome.model.Device;
import com.smarthome.exceptions.TransientDeviceException;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import com.smarthome.util.LoggerUtil;

/**
 * Proxy that wraps actual Device calls and simulates transient failures
 * with retry/backoff. Demonstrates structural Proxy pattern.
 */
public class DeviceProxy extends Device {
    private final Device real;
    private final Random rng = new Random();
    private final Logger log = LoggerUtil.getLogger(DeviceProxy.class.getName());

    public DeviceProxy(Device real) {
        super(real.getId(), real.getType());
        this.real = real;
    }

    private void withRetry(DeviceAction action) throws Exception {
        int attempts = 0;
        long backoff = 200; // ms
        while (true) {
            attempts++;
            try {
                // simulate transient error randomly
                if (rng.nextDouble() < 0.08) {
                    throw new TransientDeviceException("Simulated transient failure (device busy)"); 
                }
                action.run();
                return;
            } catch (TransientDeviceException te) {
                log.warning("Transient error on device " + real.getId() + ": " + te.getMessage() + " (attempt " + attempts + ")");
                if (attempts >= 4) throw te;
                Thread.sleep(backoff);
                backoff *= 2;
            }
        }
    }

    @Override public void turnOn() throws Exception {
        withRetry(() -> real.turnOn());
    }

    @Override public void turnOff() throws Exception {
        withRetry(() -> real.turnOff());
    }

    @Override public String status() {
        return real.status();
    }

    @Override public void update(com.smarthome.observer.Event event) {
        real.update(event);
    }

    // Functional interface for actions
    private interface DeviceAction { void run() throws Exception; }
}
