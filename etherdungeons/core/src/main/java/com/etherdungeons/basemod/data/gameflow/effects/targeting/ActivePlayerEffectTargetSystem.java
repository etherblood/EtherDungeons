package com.etherdungeons.basemod.data.gameflow.effects.targeting;

import com.etherdungeons.basemod.GameSystem;
import com.etherdungeons.basemod.data.gameflow.effects.turnflow.phases.ActiveTurn;
import com.etherdungeons.basemod.data.gameflow.triggers.TriggerRequest;
import com.etherdungeons.basemod.data.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import org.slf4j.Logger;

/**
 *
 * @author Philipp
 */
public class ActivePlayerEffectTargetSystem implements GameSystem {

    @Override
    public void run(EntityData data, Logger log) {
        for (EntityId triggerArgs : data.entities(TriggerRequest.class)) {
            EntityId effect = data.get(triggerArgs, TriggerRequest.class).getTrigger();
            ActivePlayerEffectTarget target = data.get(effect, ActivePlayerEffectTarget.class);
            if(target != null) {
                data.set(triggerArgs, new TriggerArgsTargets(data.entity(ActiveTurn.class)));
            }
        }
    }

}
