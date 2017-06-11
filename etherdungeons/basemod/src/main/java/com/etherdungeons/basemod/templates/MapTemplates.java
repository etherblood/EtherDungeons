package com.etherdungeons.basemod.templates;

import com.etherdungeons.basemod.data.core.Name;
import com.etherdungeons.basemod.data.gameflow.GameState;
import com.etherdungeons.basemod.data.gameflow.MapSize;
import com.etherdungeons.basemod.data.gameflow.effects.move.MoveEffect;
import com.etherdungeons.basemod.data.gameflow.effects.targeting.ActivePlayerEffectTarget;
import com.etherdungeons.basemod.data.gameflow.effects.turnflow.StartGameEffect;
import com.etherdungeons.basemod.data.gameflow.triggers.EndTurnTrigger;
import com.etherdungeons.basemod.data.gameflow.triggers.PostResolveTriggerRequest;
import com.etherdungeons.basemod.data.gameflow.triggers.TriggerRequest;
import com.etherdungeons.basemod.data.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.basemod.data.position.Position;
import com.etherdungeons.basemod.data.stats.active.reset.ResetApEffect;
import com.etherdungeons.basemod.data.stats.active.reset.ResetMpEffect;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import com.etherdungeons.templates.Template;
import com.etherdungeons.templates.TemplateProvider;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Philipp
 */
public class MapTemplates implements TemplateProvider {

    @Override
    public Map<String, Template> getTemplates() {
        Map<String, Template> templates = new HashMap<>();
        templates.put("test_map", MapTemplates::testMap);
        return templates;
    }

    static void testMap(EntityData data, Object... params) {
        Position[] positions = new Position[]{new Position(2, 2), new Position(17, 17), new Position(2, 17), new Position(17, 2)};
        for (int i = 0; i < Math.min(4, params.length); i++) {
            data.set((EntityId) params[i], positions[i]);
        }
        gamestate(data);
    }
    
    static void gamestate(EntityData data, Object... params) {
        EntityId gamestate = data.createEntity();
        data.set(gamestate, new Name("GameState"));
        data.set(gamestate, new GameState());
        data.set(gamestate, new MapSize(20, 20));
        
        EntityId startGameEffect = data.createEntity();
        data.set(startGameEffect, new StartGameEffect());
        data.set(startGameEffect, new PostResolveTriggerRequest(startGameEffect));
        
        EntityId endTurnActivePlayerEffects = data.createEntity();
        data.set(endTurnActivePlayerEffects, new Name("EndTurnActivePlayerEffects"));
        data.set(endTurnActivePlayerEffects, new EndTurnTrigger());
        data.set(endTurnActivePlayerEffects, new ActivePlayerEffectTarget());
        data.set(endTurnActivePlayerEffects, new ResetApEffect());
        data.set(endTurnActivePlayerEffects, new ResetMpEffect());
    }

}
