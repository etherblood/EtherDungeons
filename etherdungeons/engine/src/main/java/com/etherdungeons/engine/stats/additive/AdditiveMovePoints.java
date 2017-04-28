package com.etherdungeons.engine.stats.additive;

import com.etherdungeons.entitysystem.EntityComponent;

/**
 *
 * @author Philipp
 */
public class AdditiveMovePoints implements EntityComponent {
    private final int mp;

    public AdditiveMovePoints(int mp) {
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
