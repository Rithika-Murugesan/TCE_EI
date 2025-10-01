package com.rover;

import com.rover.direction.Direction;
import com.rover.grid.Grid;

import java.util.Objects;
import java.util.logging.Logger;

public final class Rover {
    private Position position;
    private Direction direction;
    private final Grid grid;
    private final Logger logger;

    public Rover(Position start, Direction direction, Grid grid, Logger logger) {
        this.position = Objects.requireNonNull(start, "start position cannot be null");
        this.direction = Objects.requireNonNull(direction, "direction cannot be null");
        this.grid = Objects.requireNonNull(grid, "grid cannot be null");
        this.logger = Objects.requireNonNull(logger, "logger cannot be null");
        if (!grid.isWithinBounds(start)) throw new IllegalArgumentException("Start position out of bounds");
        if (grid.hasObstacle(start)) throw new IllegalArgumentException("Start position occupied by obstacle");
    }

    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = Objects.requireNonNull(position); }

    public Direction getDirection() { return direction; }
    public void setDirection(Direction direction) { this.direction = Objects.requireNonNull(direction); }

    public Grid getGrid() { return grid; }

    public Logger getLogger() { return logger; }
}
