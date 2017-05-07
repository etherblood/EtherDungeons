package com.etherdungeons.engine.effects.endturn;

import com.etherdungeons.engine.commands.CommandHandler;
import com.etherdungeons.engine.gameflow.ActiveTurn;
import com.etherdungeons.engine.core.OwnedBy;
import com.etherdungeons.engine.gameflow.triggers.TriggerRequest;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

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
        EntityId caster = data.entity(ActiveTurn.class);
        EntityId endTurnAbility = data.streamEntities(OwnedBy.class, EndTurnTrigger.class).filter(e -> data.get(e, OwnedBy.class).getOwner() == caster).findAny().get();
        data.set(data.createEntity(), new TriggerRequest(endTurnAbility));
    }

}
