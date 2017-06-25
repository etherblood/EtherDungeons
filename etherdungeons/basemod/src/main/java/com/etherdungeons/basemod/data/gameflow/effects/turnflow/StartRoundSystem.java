package com.etherdungeons.basemod.data.gameflow.effects.turnflow;

import com.etherdungeons.basemod.GameSystem;
import com.etherdungeons.basemod.data.gameflow.Actor;
import com.etherdungeons.basemod.data.gameflow.GameState;
import com.etherdungeons.basemod.data.gameflow.effects.turnflow.phases.CurrentRound;
import com.etherdungeons.basemod.data.gameflow.effects.turnflow.phases.NextActor;
import com.etherdungeons.basemod.data.gameflow.triggers.PostResolveTriggerRequest;
import com.etherdungeons.basemod.data.gameflow.triggers.Triggered;
import com.etherdungeons.basemod.data.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.basemod.data.stats.buffed.BuffedInitiative;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Philipp
 */
public class StartRoundSystem implements GameSystem {

    private final Logger log = LoggerFactory.getLogger(StartRoundSystem.class);

    @Override
    public void run(EntityData data) {
        if (data.streamEntities(StartRoundEffect.class, Triggered.class).findAny().isPresent()) {
            Comparator<EntityId> comparator = Comparator.comparingInt(entity -> data.get(entity, BuffedInitiative.class).getInit());
            comparator = comparator.reversed();
            comparator = comparator.thenComparing(e -> e);

            EntityId state = data.entity(GameState.class);
            int round = data.get(state, CurrentRound.class).getRound() + 1;
            data.set(state, new CurrentRound(round));
            List<EntityId> actors = data.streamEntities(Actor.class, BuffedInitiative.class)
                    .sorted(comparator)
                    .collect(Collectors.toList());

            EntityId a = actors.get(0);
            for (int i = 1; i < actors.size(); i++) {
                EntityId b = actors.get(i);
                data.set(a, new NextActor(b));
                a = b;
            }

            log.info("started round {}", round);
            EntityId startTurn = data.createEntity();
            data.set(startTurn, new PostResolveTriggerRequest(startTurn));
            data.set(startTurn, new StartTurnEffect());
            data.set(startTurn, new TriggerArgsTargets(actors.get(0)));
        }
    }

}
