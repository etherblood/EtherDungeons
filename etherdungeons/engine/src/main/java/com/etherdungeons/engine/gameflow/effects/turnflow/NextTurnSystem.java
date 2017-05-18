package com.etherdungeons.engine.gameflow.effects.turnflow;

import com.etherdungeons.engine.gameflow.effects.turnflow.phases.ActiveTurn;
import com.etherdungeons.engine.gameflow.effects.turnflow.phases.NextActor;
import com.etherdungeons.engine.gameflow.triggers.PostResolveTriggerRequest;
import com.etherdungeons.engine.gameflow.triggers.Triggered;
import com.etherdungeons.engine.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class NextTurnSystem implements Runnable {

    private final EntityData data;

    public NextTurnSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
        if (data.streamEntities(NextTurnEffect.class, Triggered.class).findAny().isPresent()) {
            EntityId current = data.entity(ActiveTurn.class);
            data.remove(current, ActiveTurn.class);
            NextActor nextComp = data.get(current, NextActor.class);
            
            EntityId entity = data.createEntity();
            data.set(entity, new PostResolveTriggerRequest(entity));
            if (nextComp == null) {
                data.set(entity, new EndRoundEffect());
            } else {
                data.set(entity, new TriggerArgsTargets(nextComp.getNext()));
                data.set(entity, new StartTurnEffect());
            }
        }
    }

}
