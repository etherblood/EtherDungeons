package com.etherdungeons.engine.gameflow.effects.turnflow;

import com.etherdungeons.engine.gameflow.GameState;
import com.etherdungeons.engine.gameflow.effects.turnflow.phases.CurrentRound;
import com.etherdungeons.engine.gameflow.triggers.PostResolveTriggerRequest;
import com.etherdungeons.engine.gameflow.triggers.TriggerRequest;
import com.etherdungeons.engine.gameflow.triggers.Triggered;
import com.etherdungeons.engine.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.engine.stats.active.reset.ResetApEffect;
import com.etherdungeons.engine.stats.active.reset.ResetHealthEffect;
import com.etherdungeons.engine.stats.active.reset.ResetMpEffect;
import com.etherdungeons.engine.stats.base.BaseHealth;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Philipp
 */
public class StartGameSystem implements Runnable {

    private final Logger log = LoggerFactory.getLogger(StartGameSystem.class);
    private final EntityData data;

    public StartGameSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
        if (data.streamEntities(StartGameEffect.class, Triggered.class).findAny().isPresent()) {
            EntityId state = data.entity(GameState.class);
            data.set(state, new CurrentRound(0));
            
            EntityId initStatsEffect = data.createEntity();
            data.set(initStatsEffect, new TriggerRequest(initStatsEffect));
            data.set(initStatsEffect, new ResetApEffect());
            data.set(initStatsEffect, new ResetMpEffect());
            data.set(initStatsEffect, new ResetHealthEffect());
            data.set(initStatsEffect, new TriggerArgsTargets(data.streamEntities(BaseHealth.class).toArray(length -> new EntityId[length])));
            log.info("started game");

            EntityId startRound = data.createEntity();
            data.set(startRound, new PostResolveTriggerRequest(startRound));
            data.set(startRound, new StartRoundEffect());
        }
    }

}