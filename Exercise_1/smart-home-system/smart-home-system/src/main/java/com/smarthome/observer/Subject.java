
package com.smarthome.observer;
public interface Subject {
    void register(Observer o);
    void unregister(Observer o);
    void notifyObservers(Event event);
}
