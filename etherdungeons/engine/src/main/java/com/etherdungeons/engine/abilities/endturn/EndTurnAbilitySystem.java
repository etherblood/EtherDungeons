package com.etherdungeons.engine.abilities.endturn;

import com.etherdungeons.engine.gameflow.ActiveTurn;
import com.etherdungeons.engine.gameflow.GameFlowManager;
import com.etherdungeons.engine.gameflow.Triggered;
import com.etherdungeons.engine.relations.OwnedBy;
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
        for (EntityId triggered : data.entities(Triggered.class)) {
            EntityId ability = data.get(triggered, Triggered.class).getEntity();
            if (data.get(ability, EndTurnAbility.class) != null) {
                EntityId caster = data.get(ability, OwnedBy.class).getOwner();
                if(data.get(caster, ActiveTurn.class) == null) {
                    throw new IllegalStateException();
                }
                gameFlowManager.endTurn();
                
                data.clearEntity(triggered);
            }
        }
    }

}
