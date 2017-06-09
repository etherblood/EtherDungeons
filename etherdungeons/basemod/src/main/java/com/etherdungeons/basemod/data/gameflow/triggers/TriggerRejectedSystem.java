package com.etherdungeons.basemod.data.gameflow.triggers;

import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Philipp
 */
public class TriggerRejectedSystem implements Runnable {

    private final static Logger log = LoggerFactory.getLogger(TriggerRejectedSystem.class);
    private final EntityData data;

    public TriggerRejectedSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
        for (EntityId entity : data.entities(TriggerRejected.class)) {
            log.debug("rejected: {}", data.components(entity));
            data.clearEntity(entity);
        }
    }

}
