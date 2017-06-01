package com.etherdungeons.engine.data.stats.cost;

import com.etherdungeons.entitysystem.EntityComponent;

/**
 *
 * @author Philipp
 */
public class MovePointsCost implements EntityComponent {
    private final int mp;

    public MovePointsCost(int mp) {
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
