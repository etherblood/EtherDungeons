package com.etherdungeons.engine.gameflow.effects.move;

import com.etherdungeons.engine.commands.CommandHandler;
import com.etherdungeons.engine.gameflow.triggers.TriggerRequest;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class MoveCommandHandler implements CommandHandler<MoveCommand> {

    private final EntityData data;

    public MoveCommandHandler(EntityData data) {
        this.data = data;
    }

    @Override
    public void handle(MoveCommand command) {
        EntityId entity = data.createEntity();
        data.set(entity, command.getPosition());
        data.set(entity, new TriggerRequest(command.getEffect()));
    }

}
