package com.etherdungeons.engine.core;

import com.etherdungeons.entitysystem.EntityComponent;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class Target implements EntityComponent {
    private final EntityId target;

    public Target(EntityId target) {
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
