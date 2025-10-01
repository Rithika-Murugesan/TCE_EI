package com.rover.command;

import com.rover.Position;
import com.rover.Rover;

public final class MoveCommand implements Command {
    @Override
    public void execute(Rover rover) throws Exception {
        Position target = rover.getDirection().forward(rover.getPosition());
        if (rover.getGrid().isValid(target)) {
            rover.setPosition(target);
            rover.getLogger().info("Moved to " + target);
        } else {
            rover.getLogger().warning("Move blocked at " + target + ". Staying at " + rover.getPosition());
        }
    }
}
