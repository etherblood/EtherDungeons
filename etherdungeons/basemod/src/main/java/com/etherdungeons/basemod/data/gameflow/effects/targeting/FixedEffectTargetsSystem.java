package com.etherdungeons.basemod.data.gameflow.effects.targeting;

import com.etherdungeons.basemod.GameSystem;
import com.etherdungeons.basemod.data.gameflow.triggers.TriggerRequest;
import com.etherdungeons.basemod.data.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class FixedEffectTargetsSystem implements GameSystem {

    @Override
    public void run(EntityData data) {
        for (EntityId triggerArgs : data.entities(TriggerRequest.class)) {
            EntityId effect = data.get(triggerArgs, TriggerRequest.class).getTrigger();
            FixedEffectTargets targets = data.get(effect, FixedEffectTargets.class);
            if(targets != null) {
                data.set(triggerArgs, new TriggerArgsTargets(targets.getTargets()));
            }
        }
    }

}
