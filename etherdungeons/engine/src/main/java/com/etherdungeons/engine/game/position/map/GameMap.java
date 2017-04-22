package com.etherdungeons.engine.game.position.map;

import com.etherdungeons.engine.game.position.Position;
import com.simsilica.es.EntityId;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class GameMap {

    public void add(EntityId entity, Position pos) {

    }

    public void remove(Position pos) {

    }

    public List<Position> findPath(Position from, Position to, int maxLength) {
        List<Position> result = new ArrayList<>();
        while (from.getX() < to.getX()) {
            from = new Position(from.getX() + 1, from.getY());
            result.add(from);
        }
        while (from.getX() > to.getX()) {
            from = new Position(from.getX() - 1, from.getY());
            result.add(from);
        }

        while (from.getY() < to.getY()) {
            from = new Position(from.getX(), from.getY() + 1);
            result.add(from);
        }
        while (from.getY() > to.getY()) {
            from = new Position(from.getX(), from.getY() - 1);
            result.add(from);
        }
        if (result.size() > maxLength) {
            return null;
        }
        return result;
    }

    public boolean isEmpty(Position pos) {
        return true;
    }

}
