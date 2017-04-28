package com.etherdungeons.engine.gameflow.triggers;

import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class TriggerSystem implements Runnable {
    private final EntityData data;

    public TriggerSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
        for (EntityId entity : data.entities(Triggered.class)) {
            data.clearEntity(entity);
        }
        for (EntityId entity : data.entities(TriggerRequest.class)) {
            data.remove(entity, TriggerRequest.class);
            data.set(entity, new Triggered());
        }
    }
    
}
