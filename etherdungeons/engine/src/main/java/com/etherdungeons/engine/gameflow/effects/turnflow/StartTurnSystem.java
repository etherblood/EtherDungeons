package com.etherdungeons.engine.gameflow.effects.turnflow;

import com.etherdungeons.engine.core.Name;
import com.etherdungeons.engine.gameflow.effects.turnflow.phases.ActiveTurn;
import com.etherdungeons.engine.gameflow.triggers.StartTurnTrigger;
import com.etherdungeons.engine.gameflow.triggers.TriggerRequest;
import com.etherdungeons.engine.gameflow.triggers.Triggered;
import com.etherdungeons.engine.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Philipp
 */
public class StartTurnSystem implements Runnable {

    private final Logger log = LoggerFactory.getLogger(StartTurnSystem.class);
    private final EntityData data;

    public StartTurnSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
        EntityId entity = data.entity(StartTurnEffect.class, TriggerArgsTargets.class, Triggered.class);
        if (entity != null) {
            EntityId actor = data.get(entity, TriggerArgsTargets.class).getTarget();
            data.set(actor, new ActiveTurn());
            log.info("started turn {}", actor);
        }
    }

}
