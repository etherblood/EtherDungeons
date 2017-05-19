package com.etherdungeons.engine.gameflow.effects.turnflow;

import com.etherdungeons.engine.gameflow.effects.turnflow.phases.ActiveTurn;
import com.etherdungeons.engine.gameflow.triggers.PostResolveTriggerRequest;
import com.etherdungeons.engine.gameflow.triggers.Triggered;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Philipp
 */
public class EndTurnSystem implements Runnable {

    private final Logger log = LoggerFactory.getLogger(EndTurnSystem.class);
    private final EntityData data;

    public EndTurnSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
        if (data.streamEntities(Triggered.class).filter(e -> data.has(data.get(e, Triggered.class).getTrigger(), EndTurnEffect.class)).findAny().isPresent()) {
            log.info("ending turn {}", data.entity(ActiveTurn.class));
            EntityId entity = data.createEntity();
            data.set(entity, new PostResolveTriggerRequest(entity));
            data.set(entity, new NextTurnEffect());
        }
    }

}
