package com.etherdungeons.engine.actions.implementations;

import com.etherdungeons.engine.actions.Action;
import com.etherdungeons.engine.actions.ActionGenerator;
import com.etherdungeons.engine.data.gameflow.effects.Ability;
import com.etherdungeons.engine.data.gameflow.effects.move.MoveEffect;
import com.etherdungeons.engine.data.gameflow.effects.targeting.FixedEffectTargets;
import com.etherdungeons.engine.data.gameflow.effects.turnflow.phases.ActiveTurn;
import com.etherdungeons.engine.data.gameflow.triggers.TriggerRequest;
import com.etherdungeons.engine.data.position.Position;
import com.etherdungeons.engine.data.position.PositionUtil;
import com.etherdungeons.engine.data.stats.active.ActiveMovePoints;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class MoveGenerator implements ActionGenerator<MoveAction> {

    private final EntityData data;

    public MoveGenerator(EntityData data) {
        this.data = data;
    }

    @Override
    public void executeAction(EntityId actor, MoveAction action) {
        EntityId triggerArgs = data.createEntity();
        data.set(triggerArgs, new TriggerRequest(action.getEffect()));
        data.set(triggerArgs, action.getPosition());
    }

    @Override
    public void generateActions(EntityId actor, List<Action> actions) {
        if (data.hasAll(actor, ActiveTurn.class, Position.class)) {
            ActiveMovePoints mp = data.get(actor, ActiveMovePoints.class);
            if (mp != null && mp.getMp() >= 1) {
                for (EntityId effect : data.entities(MoveEffect.class, Ability.class)) {
                    EntityId owner = data.get(effect, Ability.class).getOwner();
                    if (actor.equals(owner)) {
                        for (Position neighbor : PositionUtil.manhattanNeighbors(data.get(actor, Position.class))) {
                            actions.add(new MoveAction(effect, neighbor));
                        }
                    }
                }
            }
        }
    }

}
