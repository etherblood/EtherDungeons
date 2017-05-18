package com.etherdungeons.engine.gameflow.triggers;

import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class TriggeredCleanupSystem implements Runnable {
    private final EntityData data;

    public TriggeredCleanupSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
        for (EntityId entity : data.entities(Triggered.class)) {
            data.clearEntity(entity);
        }
    }
    
}
