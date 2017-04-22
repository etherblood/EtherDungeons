package com.etherdungeons.engine.game.gameflow;

import com.etherdungeons.engine.game.commands.CommandHandler;
import com.etherdungeons.engine.game.health.Health;
import com.etherdungeons.engine.game.position.Position;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class EndTurnCommandHandler implements CommandHandler<EndTurnCommand> {

    private final EntityData data;
    private final Comparator<EntityId> turnOrderComparator;

    public EndTurnCommandHandler(EntityData data) {
        this.data = data;
        Comparator<EntityId> comparator = Comparator.comparingInt(entity -> data.getComponent(entity, Initiative.class).getInit());
        comparator = comparator.reversed();
        comparator = comparator.thenComparingInt(entity -> data.getComponent(entity, Health.class).getHealth());//low health characters have higher priority
        comparator = comparator.thenComparingInt(entity -> data.getComponent(entity, Position.class).getX());
        comparator = comparator.thenComparingInt(entity -> data.getComponent(entity, Position.class).getY());
        this.turnOrderComparator = comparator;
    }

    @Override
    public void handle(EndTurnCommand command) {
        EntityId current = data.findEntity(null, ActiveTurn.class);
        List<EntityId> entities = new ArrayList<>(data.findEntities(null, Initiative.class));
        Collections.sort(entities, turnOrderComparator);
        EntityId next = entities.get((entities.indexOf(current) + 1) % entities.size());
        data.removeComponent(current, ActiveTurn.class);
        data.setComponent(next, new ActiveTurn());
    }

}
