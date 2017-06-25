package com.etherdungeons.basemod.data.gameflow.triggers;

import com.etherdungeons.basemod.GameSystem;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Philipp
 */
public class TriggerRejectedSystem implements GameSystem {

    private final static Logger log = LoggerFactory.getLogger(TriggerRejectedSystem.class);

    @Override
    public void run(EntityData data) {
        for (EntityId entity : data.entities(TriggerRejected.class)) {
            log.debug("rejected: {}", data.components(entity));
            data.clearEntity(entity);
        }
    }

}
