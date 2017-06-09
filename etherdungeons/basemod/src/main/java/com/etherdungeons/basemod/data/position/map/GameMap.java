package com.etherdungeons.basemod.data.position.map;

import com.etherdungeons.basemod.data.position.Position;
import com.etherdungeons.basemod.data.position.PositionUtil;
import com.etherdungeons.entitysystem.EntityId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 * @author Philipp
 */
public class GameMap {
    private int width, height;
    private EntityId[] map;
    
    public void setSize(int width, int height) {
        if(map != null) {
            throw new IllegalStateException("map size was alrteady set");
        }
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
    
    public List<Position> legalManhattanTargetSquares(Position center, int range, boolean lineOfSightRequired) {
        return legalManhattanTargetSquares(center.getX(), center.getY(), range, lineOfSightRequired);
    }
    
    public List<Position> legalManhattanTargetSquares(int centerX, int centerY, int range, boolean lineOfSightRequired) {
        Position center = new Position(centerX, centerY);
        List<Position> result = new ArrayList<>();
        int minX = centerX - range;
        int minY = centerY - range;
        int maxX = centerX + range;
        int maxY = centerY + range;
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                if(PositionUtil.manhattanDistance(centerX, centerY, x, y) <= range) {
                    Position position = new Position(x, y);
                    if(!lineOfSightRequired || streamLineExcludeEndpoints(center, position).allMatch(p -> isEmpty(p))) {
                        result.add(position);
//                    } else {
//                        List<Position> list = streamLineExcludeEndpoints(center, position).collect(Collectors.toList());
//                        boolean matchFound = false;
//                        for (Position position1 : list) {
//                            if(position1 != null) {
//                                matchFound = true;
//                            }
//                        }
//                        if(!matchFound) {
//                            result.add(position);
//                        } else {
//                            System.out.println("");
//                        }
                    }
                }
            }
        }
        return result;
    }
    
    public Stream<Position> streamLine(Position from, Position to) {
        int distance = PositionUtil.diagonalDistance(from, to);
        return IntStream.rangeClosed(0, distance).mapToObj(i -> PositionUtil.interpolateLinear(from, to, (float)i / distance));
    }
    
    public Stream<Position> streamLineExcludeEndpoints(Position from, Position to) {
        int distance = PositionUtil.diagonalDistance(from, to);
        return IntStream.range(1, distance).mapToObj(i -> PositionUtil.interpolateLinear(from, to, (float)i / distance));
    }

    public boolean isEmpty(Position pos) {
        return get(pos) == null;
    }

    boolean isEmpty(int index) {
        return get(index) == null;
    }

    public EntityId get(Position pos) {
        return get(index(pos));
    }

    EntityId get(int index) {
        return map[index];
    }
    
    public boolean inBounds(int x, int y) {
        return 0 <= x && x < width && 0 <= y && y < height;
    }
    
    public int diagonalDistance(int from, int to) {
        return PositionUtil.diagonalDistance(x(from), y(from), x(to), y(to));
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
