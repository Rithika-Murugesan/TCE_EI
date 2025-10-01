
package com.smarthome.observer;
public class Event {
    private final String type;
    private final Object payload;
    public Event(String type, Object payload) {
        this.type = type; this.payload = payload;
    }
    public String getType() { return type; }
    public Object getPayload() { return payload; }
    @Override public String toString() { return "Event{"+type+"}"; }
}
