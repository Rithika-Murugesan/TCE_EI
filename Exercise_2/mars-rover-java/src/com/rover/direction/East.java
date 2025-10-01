package com.rover.direction;

import com.rover.Position;

public final class East implements Direction {
    @Override
    public Position forward(Position pos) { return pos.translate(1, 0); }
    @Override
    public Direction left() { return new North(); }
    @Override
    public Direction right() { return new South(); }
    @Override
    public String name() { return "E"; }
    @Override
    public String toString() { return "East"; }
}
