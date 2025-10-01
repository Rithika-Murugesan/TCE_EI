
package com.smarthome.factory;

import com.smarthome.model.*;

public class DeviceFactory {
    private int nextId = 100; // dynamic ids for devices added at runtime

    public Device createDevice(int id, String type) {
        switch(type.toLowerCase()) {
            case "light": return new Light(id);
            case "thermostat": return new Thermostat(id);
            case "door": return new DoorLock(id);
            default: throw new IllegalArgumentException("Unknown device type: " + type);
        }
    }

    public Device createDevice(String type) {
        int id = nextId++;
        return createDevice(id, type);
    }
}
