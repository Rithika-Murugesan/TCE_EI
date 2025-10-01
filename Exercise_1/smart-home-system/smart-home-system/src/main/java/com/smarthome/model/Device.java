
package com.smarthome.model;

import com.smarthome.observer.Event;
import com.smarthome.observer.Observer;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Device implements Observer {
    protected final int id;
    protected final String type;
    protected final AtomicBoolean online = new AtomicBoolean(true);

    public Device(int id, String type) {
        this.id = id; this.type = type;
    }

    public int getId() { return id; }
    public String getType() { return type; }
    public boolean isOnline() { return online.get(); }

    public abstract String status();

    public abstract void turnOn() throws Exception;
    public abstract void turnOff() throws Exception;

    // Default observer update - devices can override to react to events
    @Override
    public void update(Event event) {
        // default no-op
    }
}
