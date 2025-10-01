package com.rover.direction;

import com.rover.Position;

public final class North implements Direction {
    @Override
    public Position forward(Position pos) { return pos.translate(0, 1); }
    @Override
    public Direction left() { return new West(); }
    @Override
    public Direction right() { return new East(); }
    @Override
    public String name() { return "N"; }
    @Override
    public String toString() { return "North"; }
}
