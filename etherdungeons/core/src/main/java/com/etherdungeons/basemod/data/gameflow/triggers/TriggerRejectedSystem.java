package com.etherdungeons.basemod.data.gameflow.triggers;

import com.etherdungeons.basemod.GameSystem;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import org.slf4j.Logger;

/**
 *
 * @author Philipp
 */
public class TriggerRejectedSystem implements GameSystem {

    @Override
    public void run(EntityData data, Logger log) {
        for (EntityId entity : data.entities(TriggerRejected.class)) {
            log.debug("rejected: {}", data.components(entity));
            data.clearEntity(entity);
        }
    }

}
