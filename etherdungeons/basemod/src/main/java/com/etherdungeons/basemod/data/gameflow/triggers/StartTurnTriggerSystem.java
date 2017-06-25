package com.etherdungeons.basemod.data.gameflow.triggers;

import com.etherdungeons.basemod.GameSystem;
import com.etherdungeons.basemod.data.gameflow.effects.turnflow.StartTurnEffect;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class StartTurnTriggerSystem implements GameSystem {

    @Override
    public void run(EntityData data) {
        if (data.streamEntities(StartTurnEffect.class, Triggered.class).findAny().isPresent()) {
            for (EntityId trigger : data.entities(StartTurnTrigger.class)) {
                data.set(data.createEntity(), new TriggerRequest(trigger));
            }
        }
    }

}
