package com.etherdungeons.engine.gameflow.triggers.conditions;

import com.etherdungeons.engine.core.Target;
import com.etherdungeons.engine.gameflow.ActiveTurn;
import com.etherdungeons.engine.gameflow.triggers.TriggerRejected;
import com.etherdungeons.engine.gameflow.triggers.TriggerRequest;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class TargetActiveTurnConditionSystem implements Runnable {

    private final EntityData data;

    public TargetActiveTurnConditionSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
        for (EntityId entity : data.entities(TriggerRequest.class)) {
            EntityId trigger = data.get(entity, TriggerRequest.class).getTrigger();
            if (data.has(trigger, TargetActiveTurnCondition.class)) {
                Target targetComp = data.get(trigger, Target.class);
                if (targetComp == null || !data.has(targetComp.getTarget(), ActiveTurn.class)) {
                    data.remove(entity, TriggerRequest.class);
                    data.set(entity, new TriggerRejected(entity, "it is not targets turn"));
                }
            }
        }
    }

}
