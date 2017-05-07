package com.etherdungeons.engine.gameflow.triggers.conditions;

import com.etherdungeons.engine.gameflow.triggers.TriggerRejected;
import com.etherdungeons.engine.gameflow.triggers.TriggerRequest;
import com.etherdungeons.engine.position.Position;
import com.etherdungeons.engine.position.map.GameMap;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class ArgsPositionEmptyConditionSystem implements Runnable {

    private final EntityData data;
    private final GameMap map;

    public ArgsPositionEmptyConditionSystem(EntityData data, GameMap map) {
        this.data = data;
        this.map = map;
    }

    @Override
    public void run() {
        for (EntityId entity : data.entities(TriggerRequest.class, ArgsPositionEmptyCondition.class)) {
            Position positionComp = data.get(entity, Position.class);
            if (positionComp == null || !map.isEmpty(positionComp)) {
                data.remove(entity, TriggerRequest.class);
                data.set(entity, new TriggerRejected(entity, "args position is not empty"));
            }
        }
    }

}
