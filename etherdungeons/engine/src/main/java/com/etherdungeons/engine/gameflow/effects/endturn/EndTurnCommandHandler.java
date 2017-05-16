package com.etherdungeons.engine.gameflow.effects.endturn;

import com.etherdungeons.engine.commands.CommandHandler;
import com.etherdungeons.engine.gameflow.triggers.TriggerRequest;
import com.etherdungeons.entitysystem.EntityData;

/**
 *
 * @author Philipp
 */
public class EndTurnCommandHandler implements CommandHandler<EndTurnCommand> {

    private final EntityData data;

    public EndTurnCommandHandler(EntityData data) {
        this.data = data;
    }

    @Override
    public void handle(EndTurnCommand command) {
        data.set(data.createEntity(), new TriggerRequest(command.getEffect()));
    }

}
