package com.etherdungeons.engine.position;

/**
 *
 * @author Philipp
 */
public class PositionUtil {
    public static int manhattanDistance(Position a, Position b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }
}
