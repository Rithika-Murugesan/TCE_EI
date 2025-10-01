package com.rover.command;

import com.rover.Rover;

public final class LeftCommand implements Command {
    @Override
    public void execute(Rover rover) throws Exception {
        rover.setDirection(rover.getDirection().left());
        rover.getLogger().info("Turned left. Now facing " + rover.getDirection().name());
    }
}
