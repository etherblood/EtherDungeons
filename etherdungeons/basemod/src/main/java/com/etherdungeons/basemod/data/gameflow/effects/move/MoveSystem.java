package com.etherdungeons.basemod.data.gameflow.effects.move;

import com.etherdungeons.basemod.data.gameflow.triggers.Triggered;
import com.etherdungeons.basemod.data.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.basemod.data.position.Position;
import com.etherdungeons.basemod.data.position.map.GameMap;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class MoveSystem implements Runnable {

    private final EntityData data;
    private final GameMap map;

    public MoveSystem(EntityData data, GameMap map) {
        this.data = data;
        this.map = map;
    }

    @Override
    public void run() {
        for (EntityId triggerArgs : data.entities(Triggered.class, TriggerArgsTargets.class, Position.class)) {
            EntityId ability = data.get(triggerArgs, Triggered.class).getTrigger();
            if (data.has(ability, MoveEffect.class)) {
                EntityId target = data.get(triggerArgs, TriggerArgsTargets.class).getTarget();
                Position from = data.get(target, Position.class);
                Position to = data.get(triggerArgs, Position.class);
                data.set(target, to);
                if(from != null) {
                    map.remove(from);
                }
                map.add(target, to);
            }
        }
    }

}
