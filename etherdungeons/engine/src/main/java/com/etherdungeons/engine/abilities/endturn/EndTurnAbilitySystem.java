package com.etherdungeons.engine.abilities.endturn;

import com.etherdungeons.engine.gameflow.ActiveTurn;
import com.etherdungeons.engine.gameflow.GameFlowManager;
import com.etherdungeons.engine.gameflow.triggers.Triggered;
import com.etherdungeons.engine.core.OwnedBy;
import com.etherdungeons.engine.core.Target;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class EndTurnAbilitySystem implements Runnable {

    private final EntityData data;
    private final GameFlowManager gameFlowManager;

    public EndTurnAbilitySystem(EntityData data, GameFlowManager gameFlowManager) {
        this.data = data;
        this.gameFlowManager = gameFlowManager;
    }

    @Override
    public void run() {
        for (EntityId triggered : data.entities(Triggered.class, Target.class)) {
            EntityId ability = data.get(triggered, Target.class).getTarget();
            if (data.get(ability, EndTurnAbility.class) != null) {
                EntityId caster = data.get(ability, OwnedBy.class).getOwner();
                if(data.get(caster, ActiveTurn.class) == null) {
                    throw new IllegalStateException();
                }
                gameFlowManager.endTurn();
            }
        }
    }

}
