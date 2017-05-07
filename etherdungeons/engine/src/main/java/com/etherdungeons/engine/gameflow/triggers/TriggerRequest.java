package com.etherdungeons.engine.gameflow.triggers;

import com.etherdungeons.entitysystem.EntityComponent;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class TriggerRequest implements EntityComponent {

    private final EntityId trigger;

    public TriggerRequest(EntityId trigger) {
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
