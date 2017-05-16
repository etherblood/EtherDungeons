package com.etherdungeons.engine.gameflow.triggers.conditions;

import com.etherdungeons.engine.gameflow.phases.ActiveTurn;
import com.etherdungeons.engine.gameflow.triggers.TriggerRejected;
import com.etherdungeons.engine.gameflow.triggers.TriggerRequest;
import com.etherdungeons.engine.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class TargetsActiveTurnConditionSystem implements Runnable {

    private final EntityData data;

    public TargetsActiveTurnConditionSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
        for (EntityId entity : data.entities(TriggerRequest.class)) {
            EntityId trigger = data.get(entity, TriggerRequest.class).getTrigger();
            if (data.has(trigger, TargetActiveTurnCondition.class)) {
                TriggerArgsTargets targetComp = data.get(entity, TriggerArgsTargets.class);
                EntityId[] targets = targetComp == null ? new EntityId[0] : targetComp.getTargets();
                boolean conditionPassed = targets.length != 0;
                for (EntityId target : targets) {
                    if (!data.has(target, ActiveTurn.class)) {
                        conditionPassed = false;
                        break;
                    }
                }
                if(!conditionPassed) {
                    data.remove(entity, TriggerRequest.class);
                        data.set(entity, new TriggerRejected(entity, "it is not targets turn"));
                }
            }
        }
    }

}
