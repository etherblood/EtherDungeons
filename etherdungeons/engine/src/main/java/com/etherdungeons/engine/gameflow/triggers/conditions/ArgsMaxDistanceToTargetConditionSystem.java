package com.etherdungeons.engine.gameflow.triggers.conditions;

import com.etherdungeons.engine.gameflow.triggers.TriggerRejected;
import com.etherdungeons.engine.gameflow.triggers.TriggerRequest;
import com.etherdungeons.engine.gameflow.triggers.triggerargs.TriggerArgsTargets;
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
            EntityId effect = data.get(triggerArgs, TriggerRequest.class).getTrigger();
            ArgsMaxDistanceToTargetCondition maxDistanceCondition = data.get(effect, ArgsMaxDistanceToTargetCondition.class);
            if (maxDistanceCondition != null) {
                Position posComp = data.get(triggerArgs, Position.class);
                TriggerArgsTargets targetComp = data.get(triggerArgs, TriggerArgsTargets.class);
                EntityId[] targets = targetComp == null ? new EntityId[0] : targetComp.getTargets();
                for (EntityId target : targets) {
                    Position targetPosComp = data.get(target, Position.class);
                    if (posComp == null || targetPosComp == null || PositionUtil.manhattanDistance(posComp, targetPosComp) > maxDistanceCondition.getDistance()) {
                        data.remove(triggerArgs, TriggerRequest.class);
                        data.set(triggerArgs, new TriggerRejected(effect, "distance to " + target + "@" + targetPosComp + " is not <= " + maxDistanceCondition.getDistance()));
                        break;
                    }
                }
            }
        }
    }

}
