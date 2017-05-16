package com.etherdungeons.engine.sandbox;

import com.etherdungeons.context.Context;
import com.etherdungeons.context.ContextBuilder;
import com.etherdungeons.engine.commands.CommandDistributor;
import com.etherdungeons.engine.core.Name;
import com.etherdungeons.engine.gameflow.Actor;
import com.etherdungeons.engine.gameflow.effects.endturn.EndTurnSystem;
import com.etherdungeons.engine.gameflow.effects.endturn.EndTurnCommand;
import com.etherdungeons.engine.gameflow.effects.endturn.EndTurnCommandHandler;
import com.etherdungeons.engine.gameflow.phases.GameFlowManager;
import com.etherdungeons.engine.gameflow.GameState;
import com.etherdungeons.engine.gameflow.effects.move.MoveEffect;
import com.etherdungeons.engine.position.Position;
import com.etherdungeons.engine.position.map.GameMap;
import com.etherdungeons.engine.gameflow.effects.move.MoveSystem;
import com.etherdungeons.engine.gameflow.effects.move.MoveCommand;
import com.etherdungeons.engine.gameflow.effects.move.MoveCommandHandler;
import com.etherdungeons.engine.commands.Command;
import com.etherdungeons.engine.gameflow.effects.targeting.FixedEffectTargets;
import com.etherdungeons.engine.gameflow.effects.targeting.FixedEffectTargetsSystem;
import com.etherdungeons.engine.gameflow.triggers.TriggerRequest;
import com.etherdungeons.engine.gameflow.triggers.TriggerSystem;
import com.etherdungeons.engine.gameflow.effects.heal.HealEffect;
import com.etherdungeons.engine.gameflow.effects.heal.HealSystem;
import com.etherdungeons.engine.gameflow.effects.endturn.EndTurnEffect;
import com.etherdungeons.engine.gameflow.triggers.EndTurnTrigger;
import com.etherdungeons.engine.gameflow.triggers.TriggerRejectedSystem;
import com.etherdungeons.engine.gameflow.triggers.conditions.ArgsMaxDistanceToTargetCondition;
import com.etherdungeons.engine.gameflow.triggers.conditions.ArgsMaxDistanceToTargetConditionSystem;
import com.etherdungeons.engine.gameflow.triggers.conditions.MinRoundNumberCondition;
import com.etherdungeons.engine.gameflow.triggers.conditions.MinRoundNumberConditionSystem;
import com.etherdungeons.engine.gameflow.triggers.conditions.TargetActiveTurnCondition;
import com.etherdungeons.engine.gameflow.triggers.conditions.TargetsActiveTurnConditionSystem;
import com.etherdungeons.engine.gameflow.triggers.cost.HasTriggerCost;
import com.etherdungeons.engine.gameflow.triggers.cost.PayTriggerCostSystem;
import com.etherdungeons.engine.stats.active.ActiveHealth;
import com.etherdungeons.engine.stats.active.MaxHealthEnforceSystem;
import com.etherdungeons.engine.stats.additive.AdditiveActionPoints;
import com.etherdungeons.engine.stats.base.BaseActionPoints;
import com.etherdungeons.engine.stats.base.BaseHealth;
import com.etherdungeons.engine.stats.base.BaseInitiative;
import com.etherdungeons.engine.stats.base.BaseMovePoints;
import com.etherdungeons.engine.stats.buffed.BuffedActionPointsUpdateSystem;
import com.etherdungeons.engine.stats.buffed.BuffedHealthUpdateSystem;
import com.etherdungeons.engine.stats.buffed.BuffedInitiativeUpdateSystem;
import com.etherdungeons.engine.stats.buffed.BuffedMovePointsUpdateSystem;
import com.etherdungeons.engine.gameflow.effects.cleanup.CascadeCleanup;
import com.etherdungeons.engine.gameflow.effects.cleanup.CascadeCleanupSystem;
import com.etherdungeons.engine.gameflow.effects.cleanup.CleanupEffect;
import com.etherdungeons.engine.gameflow.effects.cleanup.CleanupSystem;
import com.etherdungeons.engine.gameflow.effects.targeting.ActivePlayerEffectTarget;
import com.etherdungeons.engine.gameflow.effects.targeting.ActivePlayerEffectTargetSystem;
import com.etherdungeons.engine.stats.cost.MovePointsCost;
import com.etherdungeons.engine.stats.reset.ResetApEffect;
import com.etherdungeons.engine.stats.reset.ResetApSystem;
import com.etherdungeons.engine.stats.reset.ResetMpEffect;
import com.etherdungeons.engine.stats.reset.ResetMpSystem;
import com.etherdungeons.entitysystem.EntityComponent;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import com.etherdungeons.entitysystem.EntityDataImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
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
        
        contextBuilder.add(FixedEffectTargetsSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(ActivePlayerEffectTargetSystem.class.getConstructor(EntityData.class));
        
        contextBuilder.add(TargetsActiveTurnConditionSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(ArgsMaxDistanceToTargetConditionSystem.class.getConstructor(EntityData.class));
//        contextBuilder.add(ArgsPositionEmptyConditionSystem.class.getConstructor(EntityData.class, GameMap.class));
        contextBuilder.add(MinRoundNumberConditionSystem.class.getConstructor(EntityData.class));
        
        contextBuilder.add(PayTriggerCostSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(TriggerRejectedSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(TriggerSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(MoveSystem.class.getConstructor(EntityData.class, GameMap.class));
        contextBuilder.add(EndTurnSystem.class.getConstructor(EntityData.class, GameFlowManager.class));
        contextBuilder.add(HealSystem.class.getConstructor(EntityData.class));
        
        contextBuilder.add(BuffedHealthUpdateSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(BuffedInitiativeUpdateSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(BuffedMovePointsUpdateSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(BuffedActionPointsUpdateSystem.class.getConstructor(EntityData.class));
        
        contextBuilder.add(ResetApSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(ResetMpSystem.class.getConstructor(EntityData.class));
        
        contextBuilder.add(MaxHealthEnforceSystem.class.getConstructor(EntityData.class));
        
        contextBuilder.add(CleanupSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(CascadeCleanupSystem.class.getConstructor(EntityData.class));
        Context context = contextBuilder.build();
        
        context.getBean(GameMap.class).setSize(10, 10);

        EntityData data = context.getBean(EntityData.class);
        
        EntityId gameState = data.createEntity();
        data.set(gameState, new Name("GameState"));
        data.set(gameState, new GameState());
        
        EntityId endTurnActivePlayerEffects = data.createEntity();
        data.set(endTurnActivePlayerEffects, new Name("EndTurnActivePlayerEffects"));
        data.set(endTurnActivePlayerEffects, new EndTurnTrigger());
        data.set(endTurnActivePlayerEffects, new ActivePlayerEffectTarget());
        data.set(endTurnActivePlayerEffects, new ResetApEffect());
        
        EntityId amara = data.createEntity();
        data.set(amara, new Name("Amara"));
        data.set(amara, new Actor());
        data.set(amara, new Position(2, 4));
        data.set(amara, new BaseInitiative(10));
        data.set(amara, new BaseHealth(17));
        data.set(amara, new BaseActionPoints(7));
        data.set(amara, new BaseMovePoints(4));
        
        EntityId amaraMove = data.createEntity();
        data.set(amaraMove, new Name("AmaraMove"));
//        data.set(amaraMove, new OwnedBy(amara));
        data.set(amaraMove, new FixedEffectTargets(amara));
        data.set(amaraMove, new MoveEffect());
        data.set(amaraMove, new HasTriggerCost(amara));
        data.set(amaraMove, new MovePointsCost(1));
//        data.set(amaraMove, new ArgsPositionEmptyCondition());
        data.set(amaraMove, new ArgsMaxDistanceToTargetCondition(1));
        data.set(amaraMove, new TargetActiveTurnCondition());
        
        EntityId amaraEndTurn = data.createEntity();
        data.set(amaraEndTurn, new Name("AmaraEndTurn"));
//        data.set(amaraEndTurn, new OwnedBy(amara));
        data.set(amaraEndTurn, new EndTurnEffect());
        data.set(amaraEndTurn, new TargetActiveTurnCondition());
        data.set(amaraEndTurn, new FixedEffectTargets(amara));
        
        EntityId amaraEndTurnReset = data.createEntity();
        data.set(amaraEndTurnReset, new Name("AmaraEndTurnReset"));
        data.set(amaraEndTurnReset, new EndTurnTrigger());
        data.set(amaraEndTurnReset, new ResetApEffect());
        data.set(amaraEndTurnReset, new ResetMpEffect());
        data.set(amaraEndTurnReset, new TargetActiveTurnCondition());
        data.set(amaraEndTurnReset, new FixedEffectTargets(amara));
        
//        EntityId amaraRegeneration = data.createEntity();
//        data.set(amaraRegeneration, new Name("AmaraRegeneration"));
        
        EntityId healEffect = data.createEntity();
        data.set(healEffect, new Name("HealEffect"));
//        data.set(healEffect, new OwnedBy(amaraRegeneration));
        data.set(healEffect, new FixedEffectTargets(amara));
        data.set(healEffect, new HealEffect(2));
        data.set(healEffect, new EndTurnTrigger());
        data.set(healEffect, new TargetActiveTurnCondition());
        
        EntityId robert = data.createEntity();
        data.set(robert, new Name("Robert"));
        data.set(robert, new Actor());
        data.set(robert, new Position(5, 7));
        data.set(robert, new BaseInitiative(9));
        data.set(robert, new BaseHealth(17));
        data.set(robert, new BaseActionPoints(3));
        data.set(robert, new BaseMovePoints(2));
        
        EntityId robertEndTurn = data.createEntity();
        data.set(robertEndTurn, new Name("RobertEndTurn"));
//        data.set(robertEndTurn, new OwnedBy(robert));
        data.set(robertEndTurn, new EndTurnEffect());
        
        
        EntityId expiringApBuff = data.createEntity();
        data.set(expiringApBuff, new Name("ExpiringApBuff"));
        
        EntityId expireEffect = data.createEntity();
        data.set(expireEffect, new EndTurnTrigger());
        data.set(expireEffect, new MinRoundNumberCondition(2));
        data.set(expireEffect, new CleanupEffect());
        data.set(expireEffect, new CascadeCleanup(expiringApBuff));
        data.set(expireEffect, new FixedEffectTargets(expiringApBuff));
        
        EntityId apBuff = data.createEntity();
        data.set(apBuff, new FixedEffectTargets(amara, robert));
        data.set(apBuff, new AdditiveActionPoints(2));
        data.set(apBuff, new CascadeCleanup(expiringApBuff));
        
        update(context);
        context.getBean(GameFlowManager.class).startGame();
        handleCommand(context, new MoveCommand(amaraMove, new Position(3, 4)));
        handleCommand(context, new MoveCommand(amaraMove, new Position(3, 3)));
        handleCommand(context, new MoveCommand(amaraMove, new Position(4, 3)));
        data.set(amara, new ActiveHealth(16));
        logState(data, log);
        handleCommand(context, new EndTurnCommand(amaraEndTurn));
        logState(data, log);
        handleCommand(context, new EndTurnCommand(robertEndTurn));
        logState(data, log);
        handleCommand(context, new EndTurnCommand(amaraEndTurn));
        logState(data, log);
        handleCommand(context, new EndTurnCommand(robertEndTurn));
        logState(data, log);
    }

    private static void logState(EntityData data, Logger log) {
        Map<EntityId, Collection<String>> map = new HashMap<>();
        for (Class<? extends EntityComponent> componentClass : data.registeredComponentClasses()) {
            for (EntityId entity : data.entities(componentClass)) {
                map.computeIfAbsent(entity, e -> new TreeSet<>()).add(data.get(entity, componentClass).toString());
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
        } while(data.streamEntities(TriggerRequest.class).findAny().isPresent());
    }

}
