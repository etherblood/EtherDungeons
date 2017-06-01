package com.etherdungeons.engine.data.stats.base;

import com.etherdungeons.entitysystem.EntityComponent;

/**
 *
 * @author Philipp
 */
public class BaseActionPoints implements EntityComponent {
    private final int ap;

    public BaseActionPoints(int ap) {
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
