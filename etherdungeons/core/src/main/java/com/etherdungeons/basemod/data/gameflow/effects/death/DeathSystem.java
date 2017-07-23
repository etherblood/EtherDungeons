package com.etherdungeons.basemod.data.gameflow.effects.death;

import com.etherdungeons.basemod.GameSystem;
import com.etherdungeons.basemod.data.gameflow.effects.cleanup.CleanupEffect;
import com.etherdungeons.basemod.data.gameflow.triggers.TriggerRequest;
import com.etherdungeons.basemod.data.gameflow.triggers.Triggered;
import com.etherdungeons.basemod.data.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import java.util.Arrays;
import org.slf4j.Logger;

/**
 *
 * @author Philipp
 */
public class DeathSystem implements GameSystem {
    
    @Override
    public void run(EntityData data, Logger log) {
        for (EntityId triggerArgs : data.entities(Triggered.class, TriggerArgsTargets.class)) {
            EntityId ability = data.get(triggerArgs, Triggered.class).getTrigger();
            if (data.has(ability, DeathEffect.class)) {
                log.info("death of " + Arrays.toString(data.get(triggerArgs, TriggerArgsTargets.class).getTargets()) + " triggered cleanup");
                EntityId cleanup = data.createEntity();
                data.set(cleanup, new TriggerRequest(cleanup));
                data.set(cleanup, data.get(triggerArgs, TriggerArgsTargets.class));
                data.set(cleanup, new CleanupEffect());
            }
        }
    }

}
