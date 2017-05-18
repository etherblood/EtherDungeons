package com.etherdungeons.engine.gameflow.effects.turnflow.endturn;

import com.etherdungeons.engine.commands.Command;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class EndTurnCommand implements Command {
    private final EntityId effect;

    public EndTurnCommand(EntityId effect) {
        this.effect = effect;
    }

    public EntityId getEffect() {
        return effect;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{effect=" + effect + '}';
    }

}
