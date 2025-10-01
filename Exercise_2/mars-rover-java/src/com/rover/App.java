package com.rover;

import com.rover.direction.*;
import com.rover.grid.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Console application entry point.
 * Usage: run and follow prompts. Enter commands as a sequence like MMRMLP.
 * Enter Q to quit.
 */
public final class App {
    public static void main(String[] args) throws Exception {
        Logger logger = Logger.getLogger("mars.rover");
        logger.setLevel(Level.INFO);
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.ALL);
        logger.addHandler(ch);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Welcome to Mars Rover Simulator (Console)");

        int width = readInt(reader, "Grid width (e.g., 10): ");
        int height = readInt(reader, "Grid height (e.g., 10): ");

        System.out.println("Enter obstacles as comma-separated pairs (x:y), e.g. 2:2,3:5  or leave blank:");
        String obsLine = reader.readLine();
        Set<Obstacle> obstacles = new HashSet<>();
        if (obsLine != null && !obsLine.trim().isEmpty()) {
            String[] parts = obsLine.split(",");
            for (String p : parts) {
                String t = p.trim();
                if (t.isEmpty()) continue;
                String[] xy = t.split(":");
                if (xy.length != 2) {
                    System.out.println("Skipping invalid obstacle entry: " + t);
                    continue;
                }
                int ox = Integer.parseInt(xy[0].trim());
                int oy = Integer.parseInt(xy[1].trim());
                obstacles.add(new Obstacle(new Position(ox, oy)));
            }
        }

        Grid grid = new Grid(width, height, obstacles);

        System.out.println("Enter start position as x y (e.g., 0 0):");
        String[] startParts = reader.readLine().trim().split("\\s+");
        int sx = Integer.parseInt(startParts[0]);
        int sy = Integer.parseInt(startParts[1]);

        System.out.println("Enter direction (N,E,S,W):");
        String dir = reader.readLine().trim().toUpperCase();
        com.rover.direction.Direction direction = switch (dir) {
            case "N" -> new North();
            case "E" -> new East();
            case "S" -> new South();
            case "W" -> new West();
            default -> throw new IllegalArgumentException("Unknown direction: " + dir);
        };

        Rover rover = new Rover(new Position(sx, sy), direction, grid, logger);
        CommandRegistry registry = new CommandRegistry();

        System.out.println("Enter command sequences (e.g. MMRMLP). 'P' prints status. 'Q' quits."); 
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;
            if (line.equalsIgnoreCase("Q")) {
                System.out.println("Quitting. Final status:");
                new com.rover.command.ReportCommand().execute(rover);
                break;
            }
            for (char c : line.toCharArray()) {
                if (Character.isWhitespace(c)) continue;
                // lookup command from registry; avoid if/else for dispatch
                var supplier = registry.get(c);
                if (supplier == null) {
                    System.out.println("Unknown command ignored: " + c);
                    continue;
                }
                var cmd = supplier.get();
                try {
                    cmd.execute(rover);
                } catch (Exception ex) {
                    logger.warning("Command execution failed: " + ex.getMessage());
                }
            }
            System.out.println("Enter next commands or Q to quit:");
        }

        System.out.println("Goodbye.");
    }

    private static int readInt(BufferedReader reader, String prompt) throws Exception {
        while (true) {
            System.out.print(prompt);
            String line = reader.readLine();
            if (line == null) throw new IllegalStateException("No input");
            line = line.trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid integer. Try again.");
            }
        }
    }
}
