package com.etherdungeons.basemod.data.gameflow.effects.cleanup;

import com.etherdungeons.basemod.GameSystem;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Philipp
 */
public class CascadeCleanupSystem implements GameSystem {
    
    private final static Logger log = LoggerFactory.getLogger(CascadeCleanupSystem.class);

    @Override
    public void run(EntityData data) {
        for (EntityId entity : data.entities(CascadeCleanup.class)) {
            tryCascadeClean(data, entity);
        }
    }
    
    private boolean tryCascadeClean(EntityData data, EntityId entity) {
        EntityId target = data.get(entity, CascadeCleanup.class).getTarget();
        if(data.isEmpty(target) || (data.has(target, CascadeCleanup.class) && tryCascadeClean(data, target))) {
            log.info("cascade cleaning entity {} with components {}", entity, data.components(entity));
            data.clearEntity(entity);
            return true;
        }
        return false;
    }

}
