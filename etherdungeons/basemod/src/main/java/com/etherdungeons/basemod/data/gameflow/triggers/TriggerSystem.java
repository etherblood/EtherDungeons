package com.etherdungeons.basemod.data.gameflow.triggers;

import com.etherdungeons.basemod.GameSystem;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class TriggerSystem implements GameSystem {

    @Override
    public void run(EntityData data) {
        for (EntityId entity : data.entities(TriggerRequest.class)) {
            TriggerRequest removed = data.remove(entity, TriggerRequest.class);
            data.set(entity, new Triggered(removed.getTrigger()));
        }
    }
    
}
