package com.etherdungeons.engine.stats.active;

import com.etherdungeons.entitysystem.EntityComponent;

/**
 *
 * @author Philipp
 */
public class ActiveActionPoints implements EntityComponent {
    private final int ap;

    public ActiveActionPoints(int ap) {
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
