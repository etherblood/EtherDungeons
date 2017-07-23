package com.etherdungeons.basemod.data.gameflow.effects.cleanup;

import com.etherdungeons.entitysystem.EntityComponent;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class CascadeCleanup implements EntityComponent {

    private final EntityId target;

    public CascadeCleanup(EntityId target) {
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
