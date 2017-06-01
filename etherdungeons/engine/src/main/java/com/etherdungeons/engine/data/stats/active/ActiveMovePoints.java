package com.etherdungeons.engine.data.stats.active;

import com.etherdungeons.entitysystem.EntityComponent;

/**
 *
 * @author Philipp
 */
public class ActiveMovePoints implements EntityComponent {
    private final int mp;

    public ActiveMovePoints(int mp) {
        this.mp = mp;
    }

    public int getMp() {
        return mp;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "mp=" + mp + '}';
    }
}
