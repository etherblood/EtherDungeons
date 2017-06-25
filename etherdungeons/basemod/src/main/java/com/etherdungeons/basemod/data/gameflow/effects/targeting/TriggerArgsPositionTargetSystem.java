package com.etherdungeons.basemod.data.gameflow.effects.targeting;

import com.etherdungeons.basemod.GameSystem;
import com.etherdungeons.basemod.data.gameflow.triggers.TriggerRequest;
import com.etherdungeons.basemod.data.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.basemod.data.position.MapUtil;
import com.etherdungeons.basemod.data.position.Position;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class TriggerArgsPositionTargetSystem implements GameSystem {

    @Override
    public void run(EntityData data) {
        for (EntityId triggerArgs : data.entities(TriggerRequest.class, Position.class)) {
            EntityId effect = data.get(triggerArgs, TriggerRequest.class).getTrigger();
            if(data.has(effect, TriggerArgsPositionTarget.class)) {
                EntityId target = MapUtil.get(data, data.get(triggerArgs, Position.class));
                if(target != null) {
                    data.set(triggerArgs, new TriggerArgsTargets(target));
                }
            }
        }
    }

}
