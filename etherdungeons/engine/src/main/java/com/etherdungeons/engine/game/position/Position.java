package com.etherdungeons.engine.game.position;

import com.simsilica.es.EntityComponent;

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
    public String toString() {
        return getClass().getSimpleName() + "{" + "x=" + x + ", y=" + y + '}';
    }
}
