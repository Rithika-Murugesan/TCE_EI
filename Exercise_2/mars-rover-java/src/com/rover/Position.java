package com.rover;

public final class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        if (x < 0 || y < 0) throw new IllegalArgumentException("Coordinates must be non-negative");
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public Position translate(int dx, int dy) {
        int nx = this.x + dx;
        int ny = this.y + dy;
        if (nx < 0 || ny < 0) throw new IllegalArgumentException("Resulting position must be non-negative");
        return new Position(nx, ny);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position p = (Position) o;
        return x == p.x && y == p.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
