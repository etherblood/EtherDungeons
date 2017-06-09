package com.etherdungeons.basemod.actions.implementations;

import com.etherdungeons.basemod.actions.Action;
import com.etherdungeons.basemod.actions.ActionGenerator;
import com.etherdungeons.basemod.data.gameflow.effects.Ability;
import com.etherdungeons.basemod.data.gameflow.effects.targeting.FixedEffectTargets;
import com.etherdungeons.basemod.data.gameflow.effects.turnflow.EndTurnEffect;
import com.etherdungeons.basemod.data.gameflow.effects.turnflow.phases.ActiveTurn;
import com.etherdungeons.basemod.data.gameflow.triggers.TriggerRequest;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class EndTurnGenerator implements ActionGenerator<EndTurnAction> {

    private final EntityData data;

    public EndTurnGenerator(EntityData data) {
        this.data = data;
    }

    @Override
    public void executeAction(EntityId actor, EndTurnAction action) {
        data.set(data.createEntity(), new TriggerRequest(action.getEndTurnEffect()));
    }

    @Override
    public void generateActions(EntityId actor, List<Action> actions) {
        if (data.has(actor, ActiveTurn.class)) {
            for (EntityId entity : data.entities(EndTurnEffect.class, Ability.class)) {
                EntityId owner = data.get(entity, Ability.class).getOwner();
                if (actor.equals(owner)) {
                    actions.add(new EndTurnAction(entity));
                }
            }
        }
    }

}
