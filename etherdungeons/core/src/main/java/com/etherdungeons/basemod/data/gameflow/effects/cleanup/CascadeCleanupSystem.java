package com.etherdungeons.basemod.data.gameflow.effects.cleanup;

import com.etherdungeons.basemod.GameSystem;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import org.slf4j.Logger;

/**
 *
 * @author Philipp
 */
public class CascadeCleanupSystem implements GameSystem {

    @Override
    public void run(EntityData data, Logger log) {
        for (EntityId entity : data.entities(CascadeCleanup.class)) {
            tryCascadeClean(data, entity, log);
        }
    }
    
    private boolean tryCascadeClean(EntityData data, EntityId entity, Logger log) {
        EntityId target = data.get(entity, CascadeCleanup.class).getTarget();
        if(data.isEmpty(target) || (data.has(target, CascadeCleanup.class) && tryCascadeClean(data, target, log))) {
            log.info("cascade cleaning entity {} with components {}", entity, data.components(entity));
            data.clearEntity(entity);
            return true;
        }
        return false;
    }

}
