package com.etherdungeons.basemod.data.gameflow.triggers.conditions;

import com.etherdungeons.basemod.GameSystem;
import com.etherdungeons.basemod.data.gameflow.effects.turnflow.phases.CurrentRound;
import com.etherdungeons.basemod.data.gameflow.triggers.TriggerRejected;
import com.etherdungeons.basemod.data.gameflow.triggers.TriggerRequest;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import org.slf4j.Logger;

/**
 *
 * @author Philipp
 */
public class MinRoundNumberConditionSystem implements GameSystem {

    @Override
    public void run(EntityData data, Logger log) {
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
