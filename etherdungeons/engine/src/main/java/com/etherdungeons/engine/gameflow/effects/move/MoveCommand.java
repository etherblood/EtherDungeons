package com.etherdungeons.engine.gameflow.effects.move;

import com.etherdungeons.engine.position.Position;
import com.etherdungeons.engine.commands.Command;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class MoveCommand implements Command {

    private final EntityId effect;
    private final Position position;

    public MoveCommand(EntityId effect, Position position) {
        this.effect = effect;
        this.position = position;
    }

    public EntityId getEffect() {
        return effect;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{effect=" + effect + ", position=" + position + '}';
    }

}
