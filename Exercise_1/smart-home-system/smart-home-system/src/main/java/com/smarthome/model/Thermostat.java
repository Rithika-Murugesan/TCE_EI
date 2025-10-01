
package com.smarthome.model;
import com.smarthome.observer.Event;
public class Thermostat extends Device {
    private volatile double temperature;

    public Thermostat(int id) { super(id, "thermostat"); this.temperature = 70.0; }

    public synchronized void setTemperature(double t) {
        this.temperature = t;
        System.out.println("Thermostat " + id + " set to " + t);
        // publish event via hub (hub will invoke update via observable)
        // Thermostat doesn't hold hub reference to avoid tight coupling; hub will poll or use registered sensors.
    }

    public double getTemperature() { return temperature; }

    @Override public synchronized void turnOn() {
        System.out.println("Thermostat " + id + " turned ON (noop)");
    }

    @Override public synchronized void turnOff() {
        System.out.println("Thermostat " + id + " turned OFF (noop)");
    }

    @Override public String status() {
        return String.format("Thermostat %d at %.1f degrees", id, temperature);
    }

    @Override public void update(Event event) {
        // thermostat could adjust behavior on hub events
    }
}
