
package com.smarthome.model;
public class Light extends Device {
    private volatile boolean on = false;

    public Light(int id) { super(id, "light"); }

    @Override public synchronized void turnOn() {
        this.on = true;
        System.out.println("Light " + id + " turned ON");
    }
    @Override public synchronized void turnOff() {
        this.on = false;
        System.out.println("Light " + id + " turned OFF");
    }
    @Override public String status() {
        return String.format("Light %d is %s", id, on?"On":"Off");
    }

    @Override public void update(com.smarthome.observer.Event event) {
        // example: react to global events
        if ("NIGHT_MODE".equals(event.getType())) {
            this.turnOff();
        }
    }
}
