package com.etherdungeons.engine.stats.additive;

import com.etherdungeons.entitysystem.EntityComponent;

/**
 *
 * @author Philipp
 */
public class AdditiveActionPoints implements EntityComponent {
    private final int ap;

    public AdditiveActionPoints(int ap) {
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
