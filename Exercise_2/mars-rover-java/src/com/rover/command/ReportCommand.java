package com.rover.command;

import com.rover.Rover;

public final class ReportCommand implements Command {
    @Override
    public void execute(Rover rover) {
        String report = String.format("Rover is at %s facing %s", rover.getPosition(), rover.getDirection().name());
        rover.getLogger().info(report);
        System.out.println(report);
    }
}
