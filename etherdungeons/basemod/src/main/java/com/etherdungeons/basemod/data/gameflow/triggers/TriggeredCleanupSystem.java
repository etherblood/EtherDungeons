package com.etherdungeons.basemod.data.gameflow.triggers;

import com.etherdungeons.basemod.GameSystem;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class TriggeredCleanupSystem implements GameSystem {

    @Override
    public void run(EntityData data) {
        for (EntityId entity : data.entities(Triggered.class)) {
            data.clearEntity(entity);
        }
    }
    
}
