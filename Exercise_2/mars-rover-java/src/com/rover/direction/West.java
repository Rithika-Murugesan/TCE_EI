package com.rover.direction;

import com.rover.Position;

public final class West implements Direction {
    @Override
    public Position forward(Position pos) { return pos.translate(-1, 0); }
    @Override
    public Direction left() { return new South(); }
    @Override
    public Direction right() { return new North(); }
    @Override
    public String name() { return "W"; }
    @Override
    public String toString() { return "West"; }
}
