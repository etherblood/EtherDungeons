package com.etherdungeons.basemod.data.gameflow.triggers;

import com.etherdungeons.basemod.GameSystem;
import com.etherdungeons.basemod.data.gameflow.effects.turnflow.EndTurnEffect;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import org.slf4j.Logger;

/**
 *
 * @author Philipp
 */
public class EndTurnTriggerSystem implements GameSystem {

    @Override
    public void run(EntityData data, Logger log) {
        if (data.streamEntities(Triggered.class).filter(e -> data.has(data.get(e, Triggered.class).getTrigger(), EndTurnEffect.class)).findAny().isPresent()) {
            for (EntityId trigger : data.entities(EndTurnTrigger.class)) {
                data.set(data.createEntity(), new TriggerRequest(trigger));
            }
        }
    }

}
