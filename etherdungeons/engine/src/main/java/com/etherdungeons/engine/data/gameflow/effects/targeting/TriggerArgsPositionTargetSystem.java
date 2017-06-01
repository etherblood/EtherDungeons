package com.etherdungeons.engine.data.gameflow.effects.targeting;

import com.etherdungeons.engine.data.gameflow.triggers.TriggerRequest;
import com.etherdungeons.engine.data.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.engine.data.position.Position;
import com.etherdungeons.engine.data.position.map.GameMap;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class TriggerArgsPositionTargetSystem implements Runnable {

    private final EntityData data;
    private final GameMap map;

    public TriggerArgsPositionTargetSystem(EntityData data, GameMap map) {
        this.data = data;
        this.map = map;
    }

    @Override
    public void run() {
        for (EntityId triggerArgs : data.entities(TriggerRequest.class, Position.class)) {
            EntityId effect = data.get(triggerArgs, TriggerRequest.class).getTrigger();
            if(data.has(effect, TriggerArgsPositionTarget.class)) {
                EntityId target = map.get(data.get(triggerArgs, Position.class));
                if(target != null) {
                    data.set(triggerArgs, new TriggerArgsTargets(target));
                }
            }
        }
    }

}
