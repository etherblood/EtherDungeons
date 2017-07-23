package com.etherdungeons.basemod.data.gameflow.effects.death;

import com.etherdungeons.basemod.GameSystem;
import com.etherdungeons.basemod.data.gameflow.triggers.TriggerRequest;
import com.etherdungeons.basemod.data.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.basemod.data.stats.active.ActiveHealth;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import org.slf4j.Logger;

/**
 *
 * @author Philipp
 */
public class NoHealthDeathSystem implements GameSystem {

    @Override
    public void run(EntityData data, Logger log) {
        for (EntityId entity : data.entities(ActiveHealth.class)) {
            int active = data.get(entity, ActiveHealth.class).getHealth();
            if(active <= 0) {
                EntityId death = data.createEntity();
                data.set(death, new TriggerRequest(death));
                data.set(death, new TriggerArgsTargets(entity));
                data.set(death, new DeathEffect());
            }
        }
    }
}
