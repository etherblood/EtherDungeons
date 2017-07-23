package com.etherdungeons.basemod.data.gameflow.triggers;

import com.etherdungeons.entitysystem.EntityComponent;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class PostResolveTriggerRequest implements EntityComponent {

    private final EntityId trigger;

    public PostResolveTriggerRequest(EntityId trigger) {
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
