
package com.smarthome.app;

import com.smarthome.hub.SmartHomeHub;
import com.smarthome.factory.DeviceFactory;
import com.smarthome.model.Device;
import com.smarthome.command.CommandInvoker;
import com.smarthome.util.LoggerUtil;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class App {
    private static final Logger log = LoggerUtil.getLogger(App.class.getName());

    public static void main(String[] args) {
        log.info("Starting Smart Home System...");

        SmartHomeHub hub = SmartHomeHub.getInstance();
        // Initialize sample devices
        DeviceFactory factory = new DeviceFactory();
        Device d1 = factory.createDevice(1, "light");
        Device d2 = factory.createDevice(2, "thermostat");
        Device d3 = factory.createDevice(3, "door");
        hub.registerDevice(d1);
        hub.registerDevice(d2);
        hub.registerDevice(d3);

        CommandInvoker invoker = new CommandInvoker(hub);

        // Start console input in a separate thread (no blind while(true) flag)
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(() -> {
            try (Scanner sc = new Scanner(System.in)) {
                System.out.println("Type 'help' to see commands. Type 'exit' to quit.");
                while (sc.hasNextLine()) {
                    String line = sc.nextLine().trim();
                    if (line.equalsIgnoreCase("exit")) {
                        break;
                    } else if (line.isEmpty()) {
                        continue;
                    }
                    try {
                        invoker.handle(line);
                    } catch (Exception e) {
                        log.severe("Error handling command: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                log.severe("Console input failed: " + e.getMessage());
            } finally {
                log.info("Shutting down...");
                hub.shutdown();
            }
        });

        // Add shutdown hook for graceful termination
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Shutdown hook triggered. Cleaning up..."); 
            hub.shutdown();
            es.shutdownNow();
        }));
    }
}
