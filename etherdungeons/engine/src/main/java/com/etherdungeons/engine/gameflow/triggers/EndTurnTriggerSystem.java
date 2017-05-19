package com.etherdungeons.engine.gameflow.triggers;

import com.etherdungeons.engine.gameflow.effects.turnflow.EndTurnEffect;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class EndTurnTriggerSystem implements Runnable {
    private final EntityData data;

    public EndTurnTriggerSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
        if (data.streamEntities(Triggered.class).filter(e -> data.has(data.get(e, Triggered.class).getTrigger(), EndTurnEffect.class)).findAny().isPresent()) {
            for (EntityId trigger : data.entities(EndTurnTrigger.class)) {
                data.set(data.createEntity(), new TriggerRequest(trigger));
            }
        }
    }

}
