package com.etherdungeons.engine.data.stats.cost;

import com.etherdungeons.entitysystem.EntityComponent;

/**
 *
 * @author Philipp
 */
public class ActionPointsCost implements EntityComponent {
    private final int ap;

    public ActionPointsCost(int ap) {
        this.ap = ap;
    }

    public int getAp() {
        return ap;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "ap=" + ap + '}';
    }
}
