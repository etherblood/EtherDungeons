package com.etherdungeons.engine.position.map;

import com.etherdungeons.engine.position.Position;
import com.etherdungeons.entitysystem.EntityId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Philipp
 */
public class GameMap {
    private int width, height;
    private EntityId[] map;
    
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        map = new EntityId[width * height];
    }
    
    public void add(EntityId entity, Position pos) {
        map[index(pos)] = entity;
    }

    public void remove(Position pos) {
        map[index(pos)] = null;
    }
    public List<Position> findPath(Position from, Position to, int maxLength) {
        List<Integer> a = new Pathfinder(this).findPath(index(from), index(to));
        if(a.size() > maxLength) {
            return null;
        }
        return a.stream().map(i -> new Position(x(i), y(i))).collect(Collectors.toList());
    }
//
//    public List<Position> findPath(Position from, Position to, int maxLength) {
//        List<Position> result = new ArrayList<>();
//        while (from.getX() < to.getX()) {
//            from = new Position(from.getX() + 1, from.getY());
//            result.add(from);
//        }
//        while (from.getX() > to.getX()) {
//            from = new Position(from.getX() - 1, from.getY());
//            result.add(from);
//        }
//
//        while (from.getY() < to.getY()) {
//            from = new Position(from.getX(), from.getY() + 1);
//            result.add(from);
//        }
//        while (from.getY() > to.getY()) {
//            from = new Position(from.getX(), from.getY() - 1);
//            result.add(from);
//        }
//        if (result.size() > maxLength) {
//            return null;
//        }
//        return result;
//    }

    public boolean isEmpty(Position pos) {
        return isEmpty(index(pos));
    }

    boolean isEmpty(int index) {
        return map[index] == null;
    }
    
    public boolean inBounds(int x, int y) {
        return 0 <= x && x < width && 0 <= y && y < height;
    }
    
    public int diagonalDistance(int from, int to) {
        return diagonalDistance(x(from), y(from), x(to), y(to));
    }
    
    public int diagonalDistance(int fromX, int fromY, int toX, int toY) {
        return Math.max(Math.abs(toX - fromX), Math.abs(toY - fromY));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    private int index(Position pos) {
        return index(pos.getX(), pos.getY());
    }
    
    int index(int x, int y) {
        return x + y * width;
    }
    
    int x(int index) {
        return index % width;
    }
    
    int y(int index) {
        return index / width;
    }

}
