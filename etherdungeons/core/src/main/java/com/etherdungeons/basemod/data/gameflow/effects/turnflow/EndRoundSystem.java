package com.etherdungeons.basemod.data.gameflow.effects.turnflow;

import com.etherdungeons.basemod.GameSystem;
import com.etherdungeons.basemod.data.gameflow.effects.turnflow.phases.CurrentRound;
import com.etherdungeons.basemod.data.gameflow.effects.turnflow.phases.NextActor;
import com.etherdungeons.basemod.data.gameflow.triggers.PostResolveTriggerRequest;
import com.etherdungeons.basemod.data.gameflow.triggers.Triggered;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import org.slf4j.Logger;

/**
 *
 * @author Philipp
 */
public class EndRoundSystem implements GameSystem {

    @Override
    public void run(EntityData data, Logger log) {
        if (data.streamEntities(EndRoundEffect.class, Triggered.class).findAny().isPresent()) {
            data.removeAllComponents(NextActor.class);
            log.info("ended round ", data.get(data.entity(CurrentRound.class), CurrentRound.class).getRound());
            EntityId entity = data.createEntity();
            data.set(entity, new PostResolveTriggerRequest(entity));
            data.set(entity, new StartRoundEffect());
        }
    }

}
