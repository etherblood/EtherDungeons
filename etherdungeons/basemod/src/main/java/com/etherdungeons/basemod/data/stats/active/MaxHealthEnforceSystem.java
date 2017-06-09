package com.etherdungeons.basemod.data.stats.active;

import com.etherdungeons.basemod.data.stats.buffed.BuffedHealth;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class MaxHealthEnforceSystem implements Runnable {
    private final EntityData data;

    public MaxHealthEnforceSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
        for (EntityId entity : data.entities(BuffedHealth.class, ActiveHealth.class)) {
            int active = data.get(entity, ActiveHealth.class).getHealth();
            int buffed = data.get(entity, BuffedHealth.class).getHealth();
                if(buffed < active) {
                    data.set(entity, new ActiveHealth(buffed));
                }
        }
    }
}
