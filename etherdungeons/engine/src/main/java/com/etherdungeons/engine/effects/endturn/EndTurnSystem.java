package com.etherdungeons.engine.effects.endturn;

import com.etherdungeons.engine.gameflow.GameFlowManager;
import com.etherdungeons.engine.gameflow.triggers.Triggered;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class EndTurnSystem implements Runnable {

    private final EntityData data;
    private final GameFlowManager gameFlowManager;

    public EndTurnSystem(EntityData data, GameFlowManager gameFlowManager) {
        this.data = data;
        this.gameFlowManager = gameFlowManager;
    }

    @Override
    public void run() {
        if(data.streamEntities(Triggered.class).filter(e -> data.has(data.get(e, Triggered.class).getTrigger(), EndTurnTrigger.class)).findAny().isPresent()) {
            gameFlowManager.endTurn();
        }
    }

}
