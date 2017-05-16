package com.etherdungeons.engine.gameflow.effects.cleanup;

import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Philipp
 */
public class CascadeCleanupSystem implements Runnable {

    private final EntityData data;
    private final static Logger log = LoggerFactory.getLogger(CascadeCleanupSystem.class);

    public CascadeCleanupSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
        for (EntityId entity : data.entities(CascadeCleanup.class)) {
            tryCascadeClean(entity);
        }
    }
    
    private boolean tryCascadeClean(EntityId entity) {
        EntityId target = data.get(entity, CascadeCleanup.class).getTarget();
        if(data.isEmpty(target) || (data.has(target, CascadeCleanup.class) && tryCascadeClean(target))) {
            log.info("cascade clearing entity {} with components {}", entity, data.components(entity));
            data.clearEntity(entity);
            return true;
        }
        return false;
    }

}
