package com.etherdungeons.basemod.actions.implementations;

import com.etherdungeons.basemod.actions.Action;
import com.etherdungeons.basemod.actions.ActionGenerator;
import com.etherdungeons.basemod.data.gameflow.effects.Ability;
import com.etherdungeons.basemod.data.gameflow.effects.spiritclaw.SpiritClawEffect;
import com.etherdungeons.basemod.data.gameflow.effects.turnflow.phases.ActiveTurn;
import com.etherdungeons.basemod.data.gameflow.triggers.TriggerRequest;
import com.etherdungeons.basemod.data.position.Position;
import com.etherdungeons.basemod.data.position.map.GameMap;
import com.etherdungeons.basemod.data.stats.active.ActiveActionPoints;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class SpiritClawGenerator implements ActionGenerator<SpiritClawAction> {

    private static final boolean LINE_OF_SIGHT = true;
    private static final int RANGE = 5;
    private static final int AP_COST = 4;

    private final EntityData data;
    private final GameMap map;

    public SpiritClawGenerator(EntityData data, GameMap map) {
        this.data = data;
        this.map = map;
    }

    @Override
    public void executeAction(EntityId actor, SpiritClawAction action) {
        EntityId triggerArgs = data.createEntity();
        data.set(triggerArgs, new TriggerRequest(action.getEffect()));
        data.set(triggerArgs, action.getPosition());
    }

    @Override
    public void generateActions(EntityId actor, List<Action> actions) {
        if (data.hasAll(actor, ActiveTurn.class, Position.class)) {
            ActiveActionPoints ap = data.get(actor, ActiveActionPoints.class);
            if (ap != null && ap.getAp() >= AP_COST) {
                for (EntityId effect : data.entities(SpiritClawEffect.class, Ability.class)) {
                    EntityId owner = data.get(effect, Ability.class).getOwner();
                    if(actor.equals(owner)) {
                        for (Position pos : map.legalManhattanTargetSquares(data.get(actor, Position.class), RANGE, LINE_OF_SIGHT)) {
                            actions.add(new SpiritClawAction(effect, pos));
                        }
                    }
                }
            }
        }
    }
}
