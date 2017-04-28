package com.etherdungeons.engine.abilities.move;

import com.etherdungeons.engine.position.Position;
import com.etherdungeons.engine.commands.Command;

/**
 *
 * @author Philipp
 */
public class MoveCommand implements Command {

    private final Position target;

    public MoveCommand(Position target) {
        this.target = target;
    }

    public Position getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{target=" + target + '}';
    }
}
