
package com.smarthome.command;
public interface Command {
    void execute() throws Exception;
    String name();
}
