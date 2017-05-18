package com.etherdungeons.engine.gameflow.effects.turnflow;

import com.etherdungeons.engine.gameflow.effects.turnflow.phases.CurrentRound;
import com.etherdungeons.engine.gameflow.effects.turnflow.phases.NextActor;
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
public class EndRoundSystem implements Runnable {

    private final Logger log = LoggerFactory.getLogger(EndRoundSystem.class);
    private final EntityData data;

    public EndRoundSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
        if (data.streamEntities(EndRoundEffect.class, Triggered.class).findAny().isPresent()) {
            data.removeAllComponents(NextActor.class);
            log.info("ended round ", data.get(data.entity(CurrentRound.class), CurrentRound.class).getRound());
            EntityId entity = data.createEntity();
            data.set(entity, new PostResolveTriggerRequest(entity));
            data.set(entity, new StartRoundEffect());
        }
    }

}
