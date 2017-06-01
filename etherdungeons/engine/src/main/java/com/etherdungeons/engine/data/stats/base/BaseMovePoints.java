package com.etherdungeons.engine.data.stats.base;

import com.etherdungeons.entitysystem.EntityComponent;

/**
 *
 * @author Philipp
 */
public class BaseMovePoints implements EntityComponent {
    private final int mp;

    public BaseMovePoints(int mp) {
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
