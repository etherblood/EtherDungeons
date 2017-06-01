package com.etherdungeons.engine.data.gameflow.triggers;

import com.etherdungeons.entitysystem.EntityComponent;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class Triggered implements EntityComponent {

    private final EntityId trigger;

    public Triggered(EntityId trigger) {
        this.trigger = trigger;
    }

    public EntityId getTrigger() {
        return trigger;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{trigger=" + trigger + '}';
    }
}
