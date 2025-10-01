package com.rover.command;

import com.rover.Rover;

public final class RightCommand implements Command {
    @Override
    public void execute(Rover rover) throws Exception {
        rover.setDirection(rover.getDirection().right());
        rover.getLogger().info("Turned right. Now facing " + rover.getDirection().name());
    }
}
