package com.etherdungeons.engine.abilities.move;

import com.etherdungeons.engine.commands.CommandHandler;
import com.etherdungeons.engine.gameflow.ActiveTurn;
import com.etherdungeons.engine.gameflow.triggers.Triggered;
import com.etherdungeons.engine.position.Position;
import com.etherdungeons.engine.core.OwnedBy;
import com.etherdungeons.engine.core.Target;
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
        EntityId caster = data.entity(ActiveTurn.class);
        EntityId moveAbility = data.streamEntities(OwnedBy.class, MoveAbility.class).filter(e -> data.get(e, OwnedBy.class).getOwner() == caster).findAny().get();
        createMoveCommandTriggeredEntity(moveAbility, command.getTarget());
    }
    
    private void createMoveCommandTriggeredEntity(EntityId ability, Position to) {
        EntityId entity = data.createEntity();
        data.set(entity, new TriggerRequest());
        data.set(entity, new Target(ability));
        data.set(entity, to);
    }

}
