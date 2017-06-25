package com.etherdungeons.basemod.data.gameflow.effects.targeting;

import com.etherdungeons.basemod.GameSystem;
import com.etherdungeons.basemod.data.gameflow.effects.Ability;
import com.etherdungeons.basemod.data.gameflow.triggers.TriggerRequest;
import com.etherdungeons.basemod.data.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class AbilityOwnerTargetSystem implements GameSystem {

    @Override
    public void run(EntityData data) {
        for (EntityId triggerArgs : data.entities(TriggerRequest.class)) {
            EntityId effect = data.get(triggerArgs, TriggerRequest.class).getTrigger();
            if(data.has(effect, AbilityOwnerTarget.class)) {
                Ability abilityOwner = data.get(effect, Ability.class);
                if(abilityOwner != null) {
                    data.set(triggerArgs, new TriggerArgsTargets(abilityOwner.getOwner()));
                }
            }
        }
    }

}
