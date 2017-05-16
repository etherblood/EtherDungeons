package com.etherdungeons.engine.gameflow.effects.targeting;

import com.etherdungeons.engine.gameflow.triggers.*;
import com.etherdungeons.engine.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class FixedEffectTargetsSystem implements Runnable {

    private final EntityData data;

    public FixedEffectTargetsSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
        for (EntityId triggerArgs : data.entities(TriggerRequest.class)) {
            EntityId effect = data.get(triggerArgs, TriggerRequest.class).getTrigger();
            FixedEffectTargets targets = data.get(effect, FixedEffectTargets.class);
            if(targets != null) {
                data.set(triggerArgs, new TriggerArgsTargets(targets.getTargets()));
            }
        }
    }

}
