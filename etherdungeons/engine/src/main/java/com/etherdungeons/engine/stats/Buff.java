package com.etherdungeons.engine.stats;

import com.etherdungeons.entitysystem.EntityComponent;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class Buff implements EntityComponent {
    private final EntityId target;

    public Buff(EntityId target) {
        this.target = target;
    }

    public EntityId getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "owner=" + target + '}';
    }
}
