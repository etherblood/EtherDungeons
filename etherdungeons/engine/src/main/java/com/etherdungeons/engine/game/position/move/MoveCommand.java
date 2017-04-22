package com.etherdungeons.engine.game.position.move;

import com.etherdungeons.engine.game.commands.PlayerCommand;
import com.etherdungeons.engine.game.position.Position;
import com.simsilica.es.EntityId;

/**
 *
 * @author Philipp
 */
public class MoveCommand implements PlayerCommand {
    private final EntityId entity;
    private final Position target;

    public MoveCommand(EntityId entity, Position target) {
        this.entity = entity;
        this.target = target;
    }

    public EntityId getEntity() {
        return entity;
    }

    public Position getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "entity=" + entity + ", target=" + target + '}';
    }
}
