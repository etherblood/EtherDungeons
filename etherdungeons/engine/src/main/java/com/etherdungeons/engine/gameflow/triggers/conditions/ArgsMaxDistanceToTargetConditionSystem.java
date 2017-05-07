package com.etherdungeons.engine.gameflow.triggers.conditions;

import com.etherdungeons.engine.core.Target;
import com.etherdungeons.engine.gameflow.ActiveTurn;
import com.etherdungeons.engine.gameflow.triggers.TriggerRejected;
import com.etherdungeons.engine.gameflow.triggers.TriggerRequest;
import com.etherdungeons.engine.position.Position;
import com.etherdungeons.engine.position.PositionUtil;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class ArgsMaxDistanceToTargetConditionSystem implements Runnable {

    private final EntityData data;

    public ArgsMaxDistanceToTargetConditionSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
        for (EntityId triggerArgs : data.entities(TriggerRequest.class)) {
            EntityId trigger = data.get(triggerArgs, TriggerRequest.class).getTrigger();
            ArgsMaxDistanceToTargetCondition maxDistanceCondition = data.get(trigger, ArgsMaxDistanceToTargetCondition.class);
            if (maxDistanceCondition != null) {
                Target targetComp = data.get(trigger, Target.class);
                Position posComp = data.get(triggerArgs, Position.class);
                Position targetPosComp = targetComp == null ? null : data.get(targetComp.getTarget(), Position.class);
                if (posComp == null || targetComp == null || PositionUtil.manhattanDistance(posComp, targetPosComp) > maxDistanceCondition.getDistance()) {
                    data.remove(triggerArgs, TriggerRequest.class);
                    data.set(triggerArgs, new TriggerRejected(trigger, "distance to " + targetPosComp + " is not <= " + maxDistanceCondition.getDistance()));
                }
            }
        }
    }

}
