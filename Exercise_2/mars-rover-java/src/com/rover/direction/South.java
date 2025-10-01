package com.rover.direction;

import com.rover.Position;

public final class South implements Direction {
    @Override
    public Position forward(Position pos) { return pos.translate(0, -1); }
    @Override
    public Direction left() { return new East(); }
    @Override
    public Direction right() { return new West(); }
    @Override
    public String name() { return "S"; }
    @Override
    public String toString() { return "South"; }
}
