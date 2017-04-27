package com.etherdungeons.engine.gameflow;

import com.etherdungeons.entitysystem.EntityComponent;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class Triggered implements EntityComponent {
    private final EntityId entity;

    public Triggered(EntityId entity) {
        this.entity = entity;
    }

    public EntityId getEntity() {
        return entity;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{triggered=" + entity + '}';
    }
}
