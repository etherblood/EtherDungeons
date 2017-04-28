package com.etherdungeons.engine.sandbox;

import com.etherdungeons.context.Context;
import com.etherdungeons.context.ContextBuilder;
import com.etherdungeons.engine.abilities.effects.Effect;
import com.etherdungeons.engine.commands.CommandDistributor;
import com.etherdungeons.engine.core.Name;
import com.etherdungeons.engine.gameflow.Actor;
import com.etherdungeons.engine.abilities.endturn.EndTurnAbility;
import com.etherdungeons.engine.abilities.endturn.EndTurnAbilitySystem;
import com.etherdungeons.engine.abilities.endturn.EndTurnCommand;
import com.etherdungeons.engine.abilities.endturn.EndTurnCommandHandler;
import com.etherdungeons.engine.gameflow.GameFlowManager;
import com.etherdungeons.engine.gameflow.GameState;
import com.etherdungeons.engine.abilities.move.MoveAbility;
import com.etherdungeons.engine.position.Position;
import com.etherdungeons.engine.position.map.GameMap;
import com.etherdungeons.engine.abilities.move.MoveAbilitySystem;
import com.etherdungeons.engine.abilities.move.MoveCommand;
import com.etherdungeons.engine.abilities.move.MoveCommandHandler;
import com.etherdungeons.engine.commands.Command;
import com.etherdungeons.engine.core.OwnedBy;
import com.etherdungeons.engine.core.Target;
import com.etherdungeons.engine.gameflow.triggers.StartTurnTrigger;
import com.etherdungeons.engine.gameflow.triggers.TriggerRequest;
import com.etherdungeons.engine.gameflow.triggers.TriggerSystem;
import com.etherdungeons.engine.relations.MemberOf;
import com.etherdungeons.engine.abilities.effects.Heal;
import com.etherdungeons.engine.abilities.effects.HealSystem;
import com.etherdungeons.engine.stats.active.ActiveHealth;
import com.etherdungeons.engine.stats.active.MaxHealthEnforceSystem;
import com.etherdungeons.engine.stats.base.BaseActionPoints;
import com.etherdungeons.engine.stats.base.BaseHealth;
import com.etherdungeons.engine.stats.base.BaseInitiative;
import com.etherdungeons.engine.stats.base.BaseMovePoints;
import com.etherdungeons.engine.stats.buffed.BuffedActionPointsUpdateSystem;
import com.etherdungeons.engine.stats.buffed.BuffedHealthUpdateSystem;
import com.etherdungeons.engine.stats.buffed.BuffedInitiativeUpdateSystem;
import com.etherdungeons.engine.stats.buffed.BuffedMovePointsUpdateSystem;
import com.etherdungeons.entitysystem.EntityComponent;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import com.etherdungeons.entitysystem.EntityDataImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Philipp
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.lang.NoSuchMethodException
     */
    public static void main(String[] args) throws NoSuchMethodException {
        Logger log = LoggerFactory.getLogger(Main.class);
        
        ContextBuilder contextBuilder = new ContextBuilder();
        contextBuilder.add(EntityDataImpl.class.getConstructor());
        contextBuilder.add(GameMap.class.getConstructor());
        contextBuilder.add(CommandDistributor.class.getConstructor(Collection.class));
        contextBuilder.add(MoveCommandHandler.class.getConstructor(EntityData.class));
        contextBuilder.add(EndTurnCommandHandler.class.getConstructor(EntityData.class));
        contextBuilder.add(GameFlowManager.class.getConstructor(EntityData.class));
        
        contextBuilder.add(TriggerSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(MoveAbilitySystem.class.getConstructor(EntityData.class, GameMap.class));
        contextBuilder.add(EndTurnAbilitySystem.class.getConstructor(EntityData.class, GameFlowManager.class));
        contextBuilder.add(HealSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(BuffedHealthUpdateSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(BuffedInitiativeUpdateSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(BuffedMovePointsUpdateSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(BuffedActionPointsUpdateSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(MaxHealthEnforceSystem.class.getConstructor(EntityData.class));
        Context context = contextBuilder.build();
        
        context.getBean(GameMap.class).setSize(10, 10);

        EntityData data = context.getBean(EntityData.class);
        
        EntityId gameState = data.createEntity();
        data.set(gameState, new Name("GameState"));
        data.set(gameState, new GameState());
        
        EntityId teamA = data.createEntity();
        data.set(teamA, new Name("TeamA"));
        EntityId teamB = data.createEntity();
        data.set(teamB, new Name("TeamB"));
        
        EntityId amara = data.createEntity();
        data.set(amara, new Name("Amara"));
        data.set(amara, new Actor());
        data.set(amara, new Position(2, 4));
        data.set(amara, new MemberOf(teamA));
        data.set(amara, new BaseInitiative(10));
        data.set(amara, new BaseHealth(17));
        data.set(amara, new BaseActionPoints(7));
        data.set(amara, new BaseMovePoints(4));
        
        EntityId amaraMove = data.createEntity();
        data.set(amaraMove, new Name("AmaraMove"));
        data.set(amaraMove, new OwnedBy(amara));
        data.set(amaraMove, new MoveAbility());
        
        EntityId amaraEndTurn = data.createEntity();
        data.set(amaraEndTurn, new Name("AmaraEndTurn"));
        data.set(amaraEndTurn, new OwnedBy(amara));
        data.set(amaraEndTurn, new EndTurnAbility());
        
        EntityId amaraRegeneration = data.createEntity();
        data.set(amaraRegeneration, new Name("AmaraRegeneration"));
        data.set(amaraRegeneration, new StartTurnTrigger());
        
        EntityId healEffect = data.createEntity();
        data.set(healEffect, new Name("HealEffect"));
        data.set(healEffect, new OwnedBy(amaraRegeneration));
        data.set(healEffect, new Target(amara));
        data.set(healEffect, new Heal(2));
        data.set(healEffect, new Effect());
        
        EntityId robert = data.createEntity();
        data.set(robert, new Name("Robert"));
        data.set(robert, new Actor());
        data.set(robert, new Position(5, 7));
        data.set(robert, new BaseInitiative(9));
        data.set(robert, new BaseHealth(17));
        data.set(robert, new BaseActionPoints(7));
        data.set(robert, new BaseMovePoints(7));
        data.set(robert, new MemberOf(teamB));
        
        update(context);
        logState(data, log);
        context.getBean(GameFlowManager.class).startGame();
        data.set(amara, new ActiveHealth(16));
        handleCommand(context, new MoveCommand(new Position(4, 3)));
        handleCommand(context, new EndTurnCommand());
        
        logState(data, log);
    }

    private static void logState(EntityData data, Logger log) {
        Map<EntityId, List<String>> map = new HashMap<>();
        for (Class<? extends EntityComponent> componentClass : data.registeredComponentClasses()) {
            for (EntityId entity : data.entities(componentClass)) {
                map.computeIfAbsent(entity, e -> new ArrayList<>()).add(data.get(entity, componentClass).toString());
            }
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        log.info("state:\n{}", gson.toJson(map));
    }

    private static void handleCommand(Context context, Command command) {
        context.getBean(CommandDistributor.class).handle(command);
        update(context);
    }

    private static void update(Context context) {
        List<Runnable> runnables = context.getBeans(Runnable.class);
        EntityData data = context.getBean(EntityData.class);
        
        do {
            for (Runnable runnable : runnables) {
                runnable.run();
            }
        } while(!data.entities(TriggerRequest.class).isEmpty());
    }

}
