package com.etherdungeons.basemod.data.position;

import com.etherdungeons.basemod.data.gameflow.Actor;
import com.etherdungeons.entitysystem.EntityDataReadonly;
import com.etherdungeons.entitysystem.EntityId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 * @author Philipp
 */
public class MapUtil {
    public static EntityId get(EntityDataReadonly data, Position pos) {
        return data.streamEntities(Actor.class, Position.class).filter(e -> data.get(e, Position.class).equals(pos)).findAny().orElse(null);
    }
    
    public static List<Position> legalManhattanTargetSquares(EntityDataReadonly data, Position center, int range, boolean lineOfSightRequired) {
        return legalManhattanTargetSquares(data, center.getX(), center.getY(), range, lineOfSightRequired);
    }
    
    public static List<Position> legalManhattanTargetSquares(EntityDataReadonly data, int centerX, int centerY, int range, boolean lineOfSightRequired) {
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
                    if(!lineOfSightRequired || streamLineExcludeEndpoints(center, position).allMatch(p -> isEmpty(data, p))) {
                        result.add(position);
                    }
                }
            }
        }
        return result;
    }
    
    public static Stream<Position> streamLine(Position from, Position to) {
        int distance = PositionUtil.diagonalDistance(from, to);
        return IntStream.rangeClosed(0, distance).mapToObj(i -> PositionUtil.interpolateLinear(from, to, (float)i / distance));
    }
    
    public static Stream<Position> streamLineExcludeEndpoints(Position from, Position to) {
        int distance = PositionUtil.diagonalDistance(from, to);
        return IntStream.range(1, distance).mapToObj(i -> PositionUtil.interpolateLinear(from, to, (float)i / distance));
    }

    public static boolean isEmpty(EntityDataReadonly data, Position pos) {
        return get(data, pos) == null;
    }
}
