
package com.smarthome.composite;
import com.smarthome.model.Device;
import java.util.ArrayList;
import java.util.List;

/**
 * Composite pattern: group multiple devices and treat them as one
 */
public class DeviceGroup extends Device {
    private final List<Device> children = new ArrayList<>();

    public DeviceGroup(int id, String name) {
        super(id, "group:" + name);
    }

    public void add(Device d) { children.add(d); }
    public void remove(Device d) { children.remove(d); }

    @Override public synchronized void turnOn() {
        children.forEach(c -> { try { c.turnOn(); } catch (Exception e) {} });
    }

    @Override public synchronized void turnOff() {
        children.forEach(c -> { try { c.turnOff(); } catch (Exception e) {} });
    }

    @Override public String status() {
        StringBuilder sb = new StringBuilder();
        sb.append("DeviceGroup " + id + " contains:\n");
        for (Device d : children) sb.append("  - "+d.status()+"\n");
        return sb.toString();
    }

    @Override public void update(com.smarthome.observer.Event event) {
        children.forEach(c -> c.update(event));
    }
}
