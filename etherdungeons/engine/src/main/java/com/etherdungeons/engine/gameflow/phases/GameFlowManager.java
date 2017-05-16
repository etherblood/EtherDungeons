package com.etherdungeons.engine.gameflow.phases;

import com.etherdungeons.engine.gameflow.triggers.TriggerRequest;
import com.etherdungeons.engine.gameflow.triggers.StartTurnTrigger;
import com.etherdungeons.engine.core.Name;
import com.etherdungeons.engine.gameflow.Actor;
import com.etherdungeons.engine.gameflow.GameState;
import com.etherdungeons.engine.gameflow.triggers.EndTurnTrigger;
import com.etherdungeons.engine.stats.active.ActiveActionPoints;
import com.etherdungeons.engine.stats.active.ActiveHealth;
import com.etherdungeons.engine.stats.active.ActiveMovePoints;
import com.etherdungeons.engine.stats.buffed.BuffedActionPoints;
import com.etherdungeons.engine.stats.buffed.BuffedHealth;
import com.etherdungeons.engine.stats.buffed.BuffedInitiative;
import com.etherdungeons.engine.stats.buffed.BuffedMovePoints;
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
public class GameFlowManager {
//TODO: refactor this class into systems
    private final EntityData data;
    private final Comparator<EntityId> turnOrderComparator;

    public GameFlowManager(EntityData data) {
        this.data = data;
        Comparator<EntityId> comparator = Comparator.comparingInt(entity -> data.get(entity, BuffedInitiative.class).getInit());
        comparator = comparator.reversed();
        comparator = comparator.thenComparing(e -> e);
        this.turnOrderComparator = comparator;
    }

    public void startGame() {
        for (EntityId entity : data.entities(BuffedHealth.class)) {
            data.set(entity, new ActiveHealth(data.get(entity, BuffedHealth.class).getHealth()));
            resetApAndMp(entity);
        }
        log().info("started game");
        startRound();
    }

    private void startRound() {
        EntityId state = data.entity(GameState.class);
        int round = 1;
        CurrentRound previousRoundComp = data.get(state, CurrentRound.class);
        if (previousRoundComp != null) {
            round += previousRoundComp.getRound();
        }
        data.set(state, new CurrentRound(round));
        List<EntityId> actors = data.streamEntities(Actor.class, BuffedInitiative.class)
                .sorted(turnOrderComparator)
                .collect(Collectors.toList());

        EntityId a = actors.get(0);
        for (int i = 1; i < actors.size(); i++) {
            EntityId b = actors.get(i);
            data.set(a, new NextActor(b));
            a = b;
        }

        log().info("started round {}", round);
        startTurn(actors.get(0));
    }

    public void endRound() {
        for (EntityId entity : data.entities(NextActor.class)) {
            data.remove(entity, NextActor.class);
        }
        startRound();
    }

    private void startTurn(EntityId actor) {
        data.set(actor, new ActiveTurn());
        
        log().info("started turn {}", data.get(actor, Name.class).getName());
        for (EntityId trigger : data.entities(StartTurnTrigger.class)) {
            data.set(data.createEntity(), new TriggerRequest(trigger));
        }
    }

    public void endTurn() {
        EntityId current = data.entity(ActiveTurn.class);
        log().info("ending turn {}", data.get(current, Name.class).getName());
        for (EntityId trigger : data.entities(EndTurnTrigger.class)) {
            data.set(data.createEntity(), new TriggerRequest(trigger));
        }
        //TODO: systems should run here...
//        resetApAndMp(current);
        data.remove(current, ActiveTurn.class);
        NextActor nextComp = data.get(current, NextActor.class);
        if (nextComp == null) {
            endRound();
        } else {
            startTurn(nextComp.getNext());
        }
    }

    private void resetApAndMp(EntityId actor) {
        BuffedActionPoints ap = data.get(actor, BuffedActionPoints.class);
        if(ap != null) {
            data.set(actor, new ActiveActionPoints(ap.getAp()));
        } else {
            data.remove(actor, ActiveActionPoints.class);
        }

        BuffedMovePoints mp = data.get(actor, BuffedMovePoints.class);
        if(mp != null) {
            data.set(actor, new ActiveMovePoints(mp.getMp()));
        } else {
            data.remove(actor, ActiveMovePoints.class);
        }
    }

    private Logger log() {
        return LoggerFactory.getLogger(getClass());
    }
}
