package com.etherdungeons.basemod.data.gameflow.triggers;

import com.etherdungeons.basemod.GameSystem;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import org.slf4j.Logger;

/**
 *
 * @author Philipp
 */
public class TriggerSystem implements GameSystem {

    @Override
    public void run(EntityData data, Logger log) {
        for (EntityId entity : data.entities(TriggerRequest.class)) {
            TriggerRequest removed = data.remove(entity, TriggerRequest.class);
            data.set(entity, new Triggered(removed.getTrigger()));
        }
    }
    
}
