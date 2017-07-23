package com.etherdungeons.basemod.data.gameflow.effects.turnflow;

import com.etherdungeons.basemod.GameSystem;
import com.etherdungeons.basemod.data.gameflow.effects.turnflow.phases.ActiveTurn;
import com.etherdungeons.basemod.data.gameflow.effects.turnflow.phases.NextActor;
import com.etherdungeons.basemod.data.gameflow.triggers.PostResolveTriggerRequest;
import com.etherdungeons.basemod.data.gameflow.triggers.Triggered;
import com.etherdungeons.basemod.data.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import org.slf4j.Logger;

/**
 *
 * @author Philipp
 */
public class NextTurnSystem implements GameSystem {

    @Override
    public void run(EntityData data, Logger log) {
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
