package com.etherdungeons.basemod.data.gameflow.effects.move;

import com.etherdungeons.basemod.data.gameflow.triggers.Triggered;
import com.etherdungeons.basemod.data.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.basemod.data.position.Position;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class MoveSystem implements Runnable {

    private final EntityData data;

    public MoveSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
        for (EntityId triggerArgs : data.entities(Triggered.class, TriggerArgsTargets.class, Position.class)) {
            EntityId ability = data.get(triggerArgs, Triggered.class).getTrigger();
            if (data.has(ability, MoveEffect.class)) {
                EntityId target = data.get(triggerArgs, TriggerArgsTargets.class).getTarget();
                Position to = data.get(triggerArgs, Position.class);
                data.set(target, to);
            }
        }
    }

}
