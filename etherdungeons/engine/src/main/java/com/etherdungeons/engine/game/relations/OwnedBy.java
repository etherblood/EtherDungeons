package com.etherdungeons.engine.game.relations;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

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
