package com.etherdungeons.basemod.data.gameflow.effects.turnflow;

import com.etherdungeons.basemod.GameSystem;
import com.etherdungeons.basemod.data.gameflow.effects.turnflow.phases.ActiveTurn;
import com.etherdungeons.basemod.data.gameflow.triggers.Triggered;
import com.etherdungeons.basemod.data.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Philipp
 */
public class StartTurnSystem implements GameSystem {

    private final Logger log = LoggerFactory.getLogger(StartTurnSystem.class);

    @Override
    public void run(EntityData data) {
        EntityId entity = data.entity(StartTurnEffect.class, TriggerArgsTargets.class, Triggered.class);
        if (entity != null) {
            EntityId actor = data.get(entity, TriggerArgsTargets.class).getTarget();
            data.set(actor, new ActiveTurn());
            log.info("started turn {}", actor);
        }
    }

}
