package com.etherdungeons.basemod.data.stats.active.reset;

import com.etherdungeons.basemod.GameSystem;
import com.etherdungeons.basemod.data.gameflow.triggers.Triggered;
import com.etherdungeons.basemod.data.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.entitysystem.EntityComponent;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import org.slf4j.Logger;

/**
 *
 * @author Philipp
 */
public abstract class AbstractResetStatSystem<ResetStatEffect extends EntityComponent> implements GameSystem {

    @Override
    public void run(EntityData data, Logger log) {
        for (EntityId triggerArgs : data.entities(Triggered.class, TriggerArgsTargets.class)) {
            EntityId trigger = data.get(triggerArgs, Triggered.class).getTrigger();
            ResetStatEffect reset = data.get(trigger, getResetStatEffectClass());
            if(reset != null) {
                for (EntityId target : data.get(triggerArgs, TriggerArgsTargets.class).getTargets()) {
                    reset(data, reset, target);
                }
            }
        }
    }
    
    protected abstract Class<ResetStatEffect> getResetStatEffectClass();
    
    protected abstract void reset(EntityData data, ResetStatEffect reset, EntityId targets);

}
