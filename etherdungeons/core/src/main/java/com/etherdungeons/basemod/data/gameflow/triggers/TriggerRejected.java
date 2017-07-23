package com.etherdungeons.basemod.data.gameflow.triggers;

import com.etherdungeons.entitysystem.EntityComponent;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class TriggerRejected implements EntityComponent {

    private final EntityId trigger;
    private final String reason;

    public TriggerRejected(EntityId trigger, String reason) {
        this.trigger = trigger;
        this.reason = reason;
    }

    public EntityId getTrigger() {
        return trigger;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{trigger=" + trigger + ", reason=" + reason + '}';
    }
}
