
package com.smarthome.hub;

import com.smarthome.model.Device;
import com.smarthome.observer.Event;
import java.util.logging.Logger;
import com.smarthome.util.LoggerUtil;

/**
 * Simple trigger rule: for this exercise only temperature triggers are implemented.
 */
public class AutomationTrigger {
    private final String metric; // e.g., "temperature"
    private final String operator; // ">" or "<" or "=="
    private final double threshold;
    private final String actionCommand; // textual command to execute (e.g., turnOff(1))
    private final Logger log = LoggerUtil.getLogger(AutomationTrigger.class.getName());

    public AutomationTrigger(String metric, String operator, double threshold, String actionCommand) {
        this.metric = metric; this.operator = operator; this.threshold = threshold; this.actionCommand = actionCommand;
    }

    @Override public String toString() {
        return String.format("Trigger[%s %s %s -> %s]", metric, operator, threshold, actionCommand);
    }

    public boolean evaluate(SmartHomeHub hub) {
        if ("temperature".equalsIgnoreCase(metric)) {
            for (Device d : hub.allDevices()) {
                if ("thermostat".equalsIgnoreCase(d.getType())) {
                    try {
                        double t = ((com.smarthome.model.Thermostat)d).getTemperature();
                        switch (operator) {
                            case ">": return t > threshold;
                            case "<": return t < threshold;
                            case "==": return t == threshold;
                        }
                    } catch (Exception e) {
                        log.warning("Failed checking thermostat: " + e.getMessage());
                    }
                }
            }
        }
        return false;
    }

    public void executeAction(SmartHomeHub hub) throws Exception {
        // for simplicity, Hub's CommandInvoker will be used by caller; here just parse basic actions
        if (actionCommand.startsWith("turnOff(")) {
            int id = Integer.parseInt(actionCommand.substring(actionCommand.indexOf('(')+1, actionCommand.indexOf(')')));
            hub.getDevice(id).ifPresent(d -> {
                try { d.turnOff(); } catch (Exception e) { log.warning("Action failed: " + e.getMessage()); }
            });
        }
    }
}
