package com.etherdungeons.engine.gameflow.effects.targeting;

import com.etherdungeons.engine.gameflow.phases.ActiveTurn;
import com.etherdungeons.engine.gameflow.triggers.*;
import com.etherdungeons.engine.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class ActivePlayerEffectTargetSystem implements Runnable {

    private final EntityData data;

    public ActivePlayerEffectTargetSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
        for (EntityId triggerArgs : data.entities(TriggerRequest.class)) {
            EntityId effect = data.get(triggerArgs, TriggerRequest.class).getTrigger();
            ActivePlayerEffectTarget target = data.get(effect, ActivePlayerEffectTarget.class);
            if(target != null) {
                data.set(triggerArgs, new TriggerArgsTargets(data.entity(ActiveTurn.class)));
            }
        }
    }

}
