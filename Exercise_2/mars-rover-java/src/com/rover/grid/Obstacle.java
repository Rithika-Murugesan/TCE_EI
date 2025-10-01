package com.rover.grid;

import com.rover.Position;

public final class Obstacle {
    private final Position position;

    public Obstacle(Position position) {
        if (position == null) throw new IllegalArgumentException("position cannot be null");
        this.position = position;
    }

    public Position getPosition() { return position; }

    @Override
    public String toString() { return "Obstacle@" + position; }
}
