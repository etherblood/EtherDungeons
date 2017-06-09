package com.etherdungeons.basemod.data.stats.active.reset;

import com.etherdungeons.basemod.data.gameflow.triggers.Triggered;
import com.etherdungeons.basemod.data.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.entitysystem.EntityComponent;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public abstract class AbstractResetStatSystem<ResetStatEffect extends EntityComponent> implements Runnable {

    protected final EntityData data;

    public AbstractResetStatSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
        for (EntityId triggerArgs : data.entities(Triggered.class, TriggerArgsTargets.class)) {
            EntityId trigger = data.get(triggerArgs, Triggered.class).getTrigger();
            ResetStatEffect reset = data.get(trigger, getResetStatEffectClass());
            if(reset != null) {
                for (EntityId target : data.get(triggerArgs, TriggerArgsTargets.class).getTargets()) {
                    reset(reset, target);
                }
            }
        }
    }
    
    protected abstract Class<ResetStatEffect> getResetStatEffectClass();
    
    protected abstract void reset(ResetStatEffect reset, EntityId targets);

}
