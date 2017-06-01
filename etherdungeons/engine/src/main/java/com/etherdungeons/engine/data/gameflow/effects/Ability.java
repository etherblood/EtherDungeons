package com.etherdungeons.engine.data.gameflow.effects;

import com.etherdungeons.entitysystem.EntityComponent;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class Ability implements EntityComponent {
    private final EntityId owner;

    public Ability(EntityId owner) {
        this.owner = owner;
    }

    public EntityId getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{owner=" + owner + '}';
    }
}
