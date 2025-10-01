package com.rover.grid;

import com.rover.Position;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class Grid {
    private final int width;
    private final int height;
    private final Set<Obstacle> obstacles;

    public Grid(int width, int height, Set<Obstacle> obstacles) {
        if (width <= 0 || height <= 0) throw new IllegalArgumentException("Grid size must be positive");
        this.width = width;
        this.height = height;
        this.obstacles = new HashSet<>();
        if (obstacles != null) {
            for (Obstacle o : obstacles) {
                if (o == null) throw new IllegalArgumentException("Obstacle cannot be null");
                this.obstacles.add(o);
            }
        }
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public boolean isWithinBounds(Position p) {
        Objects.requireNonNull(p, "position cannot be null");
        return p.getX() >= 0 && p.getX() < width && p.getY() >= 0 && p.getY() < height;
    }

    public boolean hasObstacle(Position p) {
        Objects.requireNonNull(p, "position cannot be null");
        for (Obstacle o : obstacles) {
            if (o.getPosition().equals(p)) return true;
        }
        return false;
    }

    public boolean isValid(Position p) {
        return isWithinBounds(p) && !hasObstacle(p);
    }

    public Set<Obstacle> getObstacles() { return Collections.unmodifiableSet(obstacles); }
}
