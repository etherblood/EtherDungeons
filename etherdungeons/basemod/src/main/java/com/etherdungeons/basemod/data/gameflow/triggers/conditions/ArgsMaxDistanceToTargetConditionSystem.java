package com.etherdungeons.basemod.data.gameflow.triggers.conditions;

import com.etherdungeons.basemod.GameSystem;
import com.etherdungeons.basemod.data.gameflow.triggers.TriggerRejected;
import com.etherdungeons.basemod.data.gameflow.triggers.TriggerRequest;
import com.etherdungeons.basemod.data.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.basemod.data.position.Position;
import com.etherdungeons.basemod.data.position.PositionUtil;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class ArgsMaxDistanceToTargetConditionSystem implements GameSystem {

    @Override
    public void run(EntityData data) {
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
