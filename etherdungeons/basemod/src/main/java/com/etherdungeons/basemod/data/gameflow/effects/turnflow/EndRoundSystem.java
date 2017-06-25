package com.etherdungeons.basemod.data.gameflow.effects.turnflow;

import com.etherdungeons.basemod.GameSystem;
import com.etherdungeons.basemod.data.gameflow.effects.turnflow.phases.CurrentRound;
import com.etherdungeons.basemod.data.gameflow.effects.turnflow.phases.NextActor;
import com.etherdungeons.basemod.data.gameflow.triggers.PostResolveTriggerRequest;
import com.etherdungeons.basemod.data.gameflow.triggers.Triggered;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Philipp
 */
public class EndRoundSystem implements GameSystem {

    private final Logger log = LoggerFactory.getLogger(EndRoundSystem.class);

    @Override
    public void run(EntityData data) {
        if (data.streamEntities(EndRoundEffect.class, Triggered.class).findAny().isPresent()) {
            data.removeAllComponents(NextActor.class);
            log.info("ended round ", data.get(data.entity(CurrentRound.class), CurrentRound.class).getRound());
            EntityId entity = data.createEntity();
            data.set(entity, new PostResolveTriggerRequest(entity));
            data.set(entity, new StartRoundEffect());
        }
    }

}
