package com.etherdungeons.engine.game.position.move;

import com.etherdungeons.engine.game.commands.CommandHandler;
import com.etherdungeons.engine.game.position.MovePoints;
import com.etherdungeons.engine.game.position.Position;
import com.etherdungeons.engine.game.position.map.GameMap;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class MoveCommandHandler implements CommandHandler<MoveCommand> {

    private final EntityData data;
    private final GameMap map;

    public MoveCommandHandler(EntityData data, GameMap map) {
        this.data = data;
        this.map = map;
    }

    @Override
    public void handle(MoveCommand command) {
        EntityId caster = command.getEntity();
        if(data.getComponent(caster, MoveAbility.class) == null) {
            throw new NullPointerException();
        }
        Position to = command.getTarget();
        Position from = data.getComponent(caster, Position.class);
        int mp = data.getComponent(caster, MovePoints.class).getMp();
        List<Position> path = map.findPath(from, to, mp);
        data.setComponent(caster, new MovePoints((mp - path.size())));
        data.setComponent(caster, to);
    }

}
