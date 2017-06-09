package com.etherdungeons.basemod.data.position;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class PositionUtil {

    public static int diagonalDistance(Position from, Position to) {
        return diagonalDistance(from.getX(), from.getY(), to.getX(), to.getY());
    }

    public static int diagonalDistance(int fromX, int fromY, int toX, int toY) {
        return Math.max(Math.abs(toX - fromX), Math.abs(toY - fromY));
    }

    public static int manhattanDistance(Position a, Position b) {
        return manhattanDistance(a.getX(), a.getY(), b.getX(), b.getY());
    }

    public static int manhattanDistance(int fromX, int fromY, int toX, int toY) {
        return Math.abs(fromX - toX) + Math.abs(fromY - toY);
    }

    public static List<Position> manhattanNeighbors(Position pos) {
        return Arrays.asList(
                new Position(pos.getX(), pos.getY() - 1),
                new Position(pos.getX(), pos.getY() + 1),
                new Position(pos.getX() + 1, pos.getY()),
                new Position(pos.getX() - 1, pos.getY()));
    }
    
    public static Position interpolateLinear(Position from, Position to, float weight) {
        return new Position(interpolateLinear(from.getX(), to.getX(), weight), interpolateLinear(from.getY(), to.getY(), weight));
    }
    public static int interpolateLinear(int from, int to, float weight) {
//        return (int) ((1 - weight) * from + weight * to);
        return (int) (from + weight * (to - from));
    }
}
