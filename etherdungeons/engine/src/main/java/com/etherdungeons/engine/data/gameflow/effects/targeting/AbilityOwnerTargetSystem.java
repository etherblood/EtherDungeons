package com.etherdungeons.engine.data.gameflow.effects.targeting;

import com.etherdungeons.engine.data.gameflow.effects.Ability;
import com.etherdungeons.engine.data.gameflow.effects.turnflow.phases.ActiveTurn;
import com.etherdungeons.engine.data.gameflow.triggers.TriggerRequest;
import com.etherdungeons.engine.data.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class AbilityOwnerTargetSystem implements Runnable {

    private final EntityData data;

    public AbilityOwnerTargetSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
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
