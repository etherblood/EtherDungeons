package com.etherdungeons.engine.data.position;

import com.etherdungeons.entitysystem.EntityComponent;

/**
 *
 * @author Philipp
 */
public class Position implements EntityComponent {
    private final int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        return 29 * (29 * 5 + this.x) + this.y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Position)) {
            return false;
        }
        final Position other = (Position) obj;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "x=" + x + ", y=" + y + '}';
    }
}
