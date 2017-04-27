package com.etherdungeons.engine.relations;

import com.etherdungeons.entitysystem.EntityComponent;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class OwnedBy implements EntityComponent {
    private final EntityId owner;

    public OwnedBy(EntityId owner) {
        this.owner = owner;
    }

    public EntityId getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "owner=" + owner + '}';
    }
}
