
package com.smarthome.model;
public class DoorLock extends Device {
    private volatile boolean locked = true;
    public DoorLock(int id) { super(id, "door"); }
    public synchronized void lock() { locked = true; System.out.println("Door " + id + " locked"); }
    public synchronized void unlock() { locked = false; System.out.println("Door " + id + " unlocked"); }

    @Override public synchronized void turnOn() { unlock(); }
    @Override public synchronized void turnOff() { lock(); }

    @Override public String status() {
        return String.format("Door %d is %s", id, locked?"Locked":"Unlocked");
    }
}
