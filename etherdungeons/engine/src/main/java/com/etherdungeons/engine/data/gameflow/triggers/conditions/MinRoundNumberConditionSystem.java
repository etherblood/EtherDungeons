package com.etherdungeons.engine.data.gameflow.triggers.conditions;

import com.etherdungeons.engine.data.gameflow.effects.turnflow.phases.CurrentRound;
import com.etherdungeons.engine.data.gameflow.triggers.TriggerRejected;
import com.etherdungeons.engine.data.gameflow.triggers.TriggerRequest;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class MinRoundNumberConditionSystem implements Runnable {

    private final EntityData data;

    public MinRoundNumberConditionSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
        for (EntityId entity : data.entities(TriggerRequest.class)) {
            EntityId trigger = data.get(entity, TriggerRequest.class).getTrigger();
            if (data.has(trigger, MinRoundNumberCondition.class)) {
                int round = data.get(data.entity(CurrentRound.class), CurrentRound.class).getRound();
                int minRound = data.get(trigger, MinRoundNumberCondition.class).getRound();
                if (round < minRound) {
                    data.remove(entity, TriggerRequest.class);
                    data.set(entity, new TriggerRejected(entity, "it is not round: " + minRound + " or later"));
                }
            }
        }
    }

}
