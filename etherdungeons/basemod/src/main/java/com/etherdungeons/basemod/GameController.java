package com.etherdungeons.basemod;

import com.etherdungeons.basemod.actions.Action;
import com.etherdungeons.basemod.actions.ActionManager;
import com.etherdungeons.basemod.data.gameflow.triggers.TriggerRequest;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class GameController {
    
    private final EntityData data;
    private final ActionManager manager;
    private final List<GameSystem> systems;

    public GameController(EntityData data, ActionManager manager, List<GameSystem> systems) {
        this.data = data;
        this.manager = manager;
        this.systems = systems;
    }
    
    public void startGame() {
        updateGame();
    }
    
    public boolean applyAction(EntityId actor, Action action) {
        if(manager.isValid(actor, action)) {
            applyValidAction(actor, action);
            return true;
        }
        return false;
    }
    
    public void applyValidAction(EntityId actor, Action action) {
        assert manager.isValid(actor, action);
        manager.handle(actor, action);
        updateGame();
    }
    
    private void updateGame() {
        do {
            for (GameSystem runnable : systems) {
                runnable.run(data);
            }
        } while (data.streamEntities(TriggerRequest.class).findAny().isPresent());
    }
}
