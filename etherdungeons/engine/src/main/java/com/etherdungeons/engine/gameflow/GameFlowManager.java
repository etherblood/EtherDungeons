package com.etherdungeons.engine.gameflow;

import com.etherdungeons.engine.core.Name;
import com.etherdungeons.engine.stats.Health;
import com.etherdungeons.engine.position.Position;
import com.etherdungeons.engine.abilities.move.MoveAbility;
import com.etherdungeons.engine.relations.OwnedBy;
import com.etherdungeons.engine.relations.TeamMemberOf;
import com.etherdungeons.engine.stats.ActionPoints;
import com.etherdungeons.engine.stats.CharacterBase;
import com.etherdungeons.engine.stats.MovePoints;
import com.etherdungeons.es.extension.ZayesUtil;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Philipp
 */
public class GameFlowManager {

    private final EntityData data;
    private final Comparator<EntityId> turnOrderComparator;

    public GameFlowManager(EntityData data) {
        this.data = data;
        Comparator<EntityId> comparator = Comparator.comparingInt(entity -> data.get(entity, Initiative.class).getInit());
        comparator = comparator.reversed();
        comparator = comparator.thenComparingInt(entity -> data.get(entity, Health.class).getHealth());//low health characters have higher priority
        comparator = comparator.thenComparingInt(entity -> data.get(entity, Position.class).getX());
        comparator = comparator.thenComparingInt(entity -> data.get(entity, Position.class).getY());
        this.turnOrderComparator = comparator;
    }

    public void startGame() {
        for (EntityId base : data.entities(CharacterBase.class, OwnedBy.class)) {
            EntityId owner = data.get(base, OwnedBy.class).getOwner();
            ZayesUtil.copyExistingComponents(data, base, owner, Health.class, Initiative.class, MoveAbility.class, TeamMemberOf.class);
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
        List<EntityId> actors = new ArrayList<>(data.entities(Actor.class, Initiative.class));
        Collections.sort(actors, turnOrderComparator);

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
        startRound();
    }

    private void startTurn(EntityId actor) {
        data.set(actor, new ActiveTurn());

        EntityId baseEntity = data.streamEntities(OwnedBy.class, CharacterBase.class).filter(e -> data.get(e, OwnedBy.class).getOwner() == actor).findAny().get();
        ZayesUtil.copyExistingComponents(data, baseEntity, actor, ActionPoints.class, MovePoints.class);
        log().info("started turn {}", data.get(actor, Name.class).getName());
    }

    public void endTurn() {
        EntityId current = data.entity(ActiveTurn.class);
        data.remove(current, ActiveTurn.class);
        NextActor nextComp = data.get(current, NextActor.class);
        if (nextComp == null) {
            endRound();
        } else {
            startTurn(nextComp.getNext());
        }
    }

    private Logger log() {
        return LoggerFactory.getLogger(getClass());
    }
}
