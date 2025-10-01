package com.rover.direction;

import com.rover.Position;

public interface Direction {
    Position forward(Position pos);
    Direction left();
    Direction right();
    String name();
}
