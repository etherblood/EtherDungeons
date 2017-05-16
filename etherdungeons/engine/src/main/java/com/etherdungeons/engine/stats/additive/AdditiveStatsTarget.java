package com.etherdungeons.engine.stats.additive;

import com.etherdungeons.entitysystem.EntityComponent;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class AdditiveStatsTarget implements EntityComponent {

    private final EntityId target;

    public AdditiveStatsTarget(EntityId target) {
        this.target = target;
    }

    public EntityId getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{target=" + target + '}';
    }
}
