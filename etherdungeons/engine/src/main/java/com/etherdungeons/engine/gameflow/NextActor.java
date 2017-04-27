package com.etherdungeons.engine.gameflow;

import com.etherdungeons.entitysystem.EntityComponent;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class NextActor implements EntityComponent {

    private final EntityId nextActor;

    public NextActor(EntityId nextActor) {
        this.nextActor = nextActor;
    }

    public EntityId getNext() {
        return nextActor;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{nextActor=" + nextActor + '}';
    }
}
