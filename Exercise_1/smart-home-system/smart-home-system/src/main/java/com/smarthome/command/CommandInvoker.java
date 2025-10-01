
package com.smarthome.command;

import com.smarthome.hub.SmartHomeHub;
import com.smarthome.exceptions.InvalidCommandException;
import java.util.logging.Logger;
import com.smarthome.util.LoggerUtil;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Basic parser that interprets textual commands and executes corresponding actions.
 * Demonstrates Command pattern for user interaction & scheduling.
 */
public class CommandInvoker {
    private final SmartHomeHub hub;
    private final Logger log = LoggerUtil.getLogger(CommandInvoker.class.getName());

    public CommandInvoker(SmartHomeHub hub) { this.hub = hub; }

    public void handle(String input) throws Exception {
        // basic commands: status, help, turnOn(id), turnOff(id), setTemp(id,val), setSchedule(id,HH:mm,cmd), addTrigger metric op val action
        if (input.equalsIgnoreCase("status")) {
            hub.allDevices().forEach(d -> System.out.println(d.status()));
            return;
        }
        if (input.equalsIgnoreCase("help")) {
            System.out.println("Commands: status, help, turnOn(id), turnOff(id), setTemp(id,val), setSchedule(id,HH:mm,cmd), addTrigger metric op val action, addDevice type, removeDevice id");
            return;
        }
        if (input.startsWith("turnOn(")) {
            int id = extractInt(input);
            var device = hub.getDevice(id).orElseThrow(() -> new InvalidCommandException("Device not found: " + id));
            device.turnOn();
            return;
        }
        if (input.startsWith("turnOff(")) {
            int id = extractInt(input);
            var device = hub.getDevice(id).orElseThrow(() -> new InvalidCommandException("Device not found: " + id));
            device.turnOff();
            return;
        }
        if (input.startsWith("setTemp(")) {
            // setTemp(id,val)
            Pattern p = Pattern.compile("setTemp\\((\\d+),(\\s*[0-9.]+)\\)", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(input);
            if (m.find()) {
                int id = Integer.parseInt(m.group(1));
                double val = Double.parseDouble(m.group(2));
                var d = hub.getDevice(id).orElseThrow(() -> new InvalidCommandException("Device not found: " + id));
                if ("thermostat".equalsIgnoreCase(d.getType())) {
                    ((com.smarthome.model.Thermostat)d).setTemperature(val);
                    // notify observers & triggers
                    hub.broadcastEvent("temperatureChanged", val);
                } else throw new InvalidCommandException("Device " + id + " is not a thermostat");
            } else throw new InvalidCommandException("Invalid setTemp syntax. Use: setTemp(id,val)"); 
            return;
        }
        if (input.startsWith("setSchedule(")) {
            // setSchedule(id, HH:mm, command)
            Pattern p = Pattern.compile("setSchedule\\((\\d+),(\\s*\\d{1,2}:\\d{2}),(.*)\\)", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(input);
            if (m.find()) {
                String time = m.group(2).trim();
                String cmd = m.group(3).trim();
                // schedule the command (basic lambda)
                hub.scheduleCommand(time, () -> {
                    try { handle(cmd); } catch (Exception e) { log.warning("Scheduled command exec failed: " + e.getMessage()); }
                });
                System.out.println("Scheduled: " + cmd + " at " + time);
            } else throw new InvalidCommandException("Invalid setSchedule syntax. Use: setSchedule(id,HH:mm,command)"); 
            return;
        }
        if (input.startsWith("addTrigger")) {
            // addTrigger metric op value action
            String[] parts = input.split("\\\\s+", 5);
            if (parts.length < 5) throw new InvalidCommandException("Usage: addTrigger metric op value action");
            String metric = parts[1];
            String op = parts[2];
            double val = Double.parseDouble(parts[3]);
            String action = parts[4];
            hub.addTrigger(new com.smarthome.hub.AutomationTrigger(metric, op, val, action));
            return;
        }
        if (input.startsWith("addDevice")) {
            // addDevice type
            String[] parts = input.split("\\\\s+", 2);
            if (parts.length < 2) throw new InvalidCommandException("Usage: addDevice type");
            String type = parts[1].trim();
            var d = hub.factory.createDevice(type); // factory field is package-private in hub; it's ok for demo
            hub.registerDevice(d);
            System.out.println("Added device " + d.getId() + " type=" + d.getType());
            return;
        }
        if (input.startsWith("removeDevice")) {
            String[] parts = input.split("\\\\s+", 2);
            int id = Integer.parseInt(parts[1].trim());
            hub.unregisterDevice(id);
            System.out.println("Removed device " + id);
            return;
        }
        throw new InvalidCommandException("Unrecognized command: " + input);
    }

    private int extractInt(String s) {
        return Integer.parseInt(s.replaceAll("[^0-9]", ""));
    }
}
