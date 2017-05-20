package com.etherdungeons.engine.sandbox;

import com.etherdungeons.context.Context;
import com.etherdungeons.context.ContextBuilder;
import com.etherdungeons.engine.commands.CommandDistributor;
import com.etherdungeons.engine.core.Name;
import com.etherdungeons.engine.gameflow.Actor;
import com.etherdungeons.engine.gameflow.GameState;
import com.etherdungeons.engine.gameflow.effects.cleanup.CascadeCleanup;
import com.etherdungeons.engine.gameflow.effects.cleanup.CascadeCleanupSystem;
import com.etherdungeons.engine.gameflow.effects.cleanup.CleanupEffect;
import com.etherdungeons.engine.gameflow.effects.cleanup.CleanupSystem;
import com.etherdungeons.engine.gameflow.effects.heal.HealEffect;
import com.etherdungeons.engine.gameflow.effects.heal.HealSystem;
import com.etherdungeons.engine.gameflow.effects.move.MoveCommandHandler;
import com.etherdungeons.engine.gameflow.effects.move.MoveEffect;
import com.etherdungeons.engine.gameflow.effects.move.MoveSystem;
import com.etherdungeons.engine.gameflow.effects.targeting.ActivePlayerEffectTarget;
import com.etherdungeons.engine.gameflow.effects.targeting.ActivePlayerEffectTargetSystem;
import com.etherdungeons.engine.gameflow.effects.targeting.FixedEffectTargets;
import com.etherdungeons.engine.gameflow.effects.targeting.FixedEffectTargetsSystem;
import com.etherdungeons.engine.gameflow.effects.turnflow.EndRoundSystem;
import com.etherdungeons.engine.gameflow.effects.turnflow.EndTurnEffect;
import com.etherdungeons.engine.gameflow.effects.turnflow.EndTurnSystem;
import com.etherdungeons.engine.gameflow.effects.turnflow.NextTurnSystem;
import com.etherdungeons.engine.gameflow.effects.turnflow.StartGameEffect;
import com.etherdungeons.engine.gameflow.effects.turnflow.StartGameSystem;
import com.etherdungeons.engine.gameflow.effects.turnflow.StartRoundSystem;
import com.etherdungeons.engine.gameflow.effects.turnflow.StartTurnSystem;
import com.etherdungeons.engine.gameflow.effects.turnflow.endturn.EndTurnCommandHandler;
import com.etherdungeons.engine.gameflow.triggers.EndTurnTrigger;
import com.etherdungeons.engine.gameflow.triggers.EndTurnTriggerSystem;
import com.etherdungeons.engine.gameflow.triggers.PostResolveTriggerRequest;
import com.etherdungeons.engine.gameflow.triggers.PostResolveTriggerSystem;
import com.etherdungeons.engine.gameflow.triggers.StartTurnTriggerSystem;
import com.etherdungeons.engine.gameflow.triggers.TriggerRejectedSystem;
import com.etherdungeons.engine.gameflow.triggers.TriggerSystem;
import com.etherdungeons.engine.gameflow.triggers.TriggeredCleanupSystem;
import com.etherdungeons.engine.gameflow.triggers.conditions.ArgsMaxDistanceToTargetConditionSystem;
import com.etherdungeons.engine.gameflow.triggers.conditions.MinRoundNumberCondition;
import com.etherdungeons.engine.gameflow.triggers.conditions.MinRoundNumberConditionSystem;
import com.etherdungeons.engine.gameflow.triggers.conditions.TargetActiveTurnCondition;
import com.etherdungeons.engine.gameflow.triggers.conditions.TargetsActiveTurnConditionSystem;
import com.etherdungeons.engine.gameflow.triggers.cost.HasTriggerCost;
import com.etherdungeons.engine.gameflow.triggers.cost.PayTriggerCostSystem;
import com.etherdungeons.engine.position.Position;
import com.etherdungeons.engine.position.map.GameMap;
import com.etherdungeons.engine.stats.active.MaxHealthEnforceSystem;
import com.etherdungeons.engine.stats.active.reset.ResetApEffect;
import com.etherdungeons.engine.stats.active.reset.ResetApSystem;
import com.etherdungeons.engine.stats.active.reset.ResetHealthSystem;
import com.etherdungeons.engine.stats.active.reset.ResetMpEffect;
import com.etherdungeons.engine.stats.active.reset.ResetMpSystem;
import com.etherdungeons.engine.stats.additive.AdditiveActionPoints;
import com.etherdungeons.engine.stats.base.BaseActionPoints;
import com.etherdungeons.engine.stats.base.BaseHealth;
import com.etherdungeons.engine.stats.base.BaseInitiative;
import com.etherdungeons.engine.stats.base.BaseMovePoints;
import com.etherdungeons.engine.stats.buffed.BuffedActionPointsUpdateSystem;
import com.etherdungeons.engine.stats.buffed.BuffedHealthUpdateSystem;
import com.etherdungeons.engine.stats.buffed.BuffedInitiativeUpdateSystem;
import com.etherdungeons.engine.stats.buffed.BuffedMovePointsUpdateSystem;
import com.etherdungeons.engine.stats.cost.MovePointsCost;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityDataImpl;
import com.etherdungeons.entitysystem.EntityId;
import com.etherdungeons.templates.TemplateImporter;
import java.util.Collection;

/**
 *
 * @author Philipp
 */
public class Setup {
    public Context createContext() throws NoSuchMethodException {
        ContextBuilder contextBuilder = new ContextBuilder();
        contextBuilder.add(EntityDataImpl.class.getConstructor());
        contextBuilder.add(GameMap.class.getConstructor());
        contextBuilder.add(CommandDistributor.class.getConstructor(Collection.class));
        contextBuilder.add(MoveCommandHandler.class.getConstructor(EntityData.class));
        contextBuilder.add(EndTurnCommandHandler.class.getConstructor(EntityData.class));

        contextBuilder.add(FixedEffectTargetsSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(ActivePlayerEffectTargetSystem.class.getConstructor(EntityData.class));

        contextBuilder.add(TargetsActiveTurnConditionSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(ArgsMaxDistanceToTargetConditionSystem.class.getConstructor(EntityData.class));
//        contextBuilder.add(ArgsPositionEmptyConditionSystem.class.getConstructor(EntityData.class, GameMap.class));
        contextBuilder.add(MinRoundNumberConditionSystem.class.getConstructor(EntityData.class));

        contextBuilder.add(PayTriggerCostSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(TriggerRejectedSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(TriggerSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(StartTurnTriggerSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(EndTurnTriggerSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(MoveSystem.class.getConstructor(EntityData.class, GameMap.class));
        contextBuilder.add(EndTurnSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(NextTurnSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(StartTurnSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(EndRoundSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(StartRoundSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(StartGameSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(HealSystem.class.getConstructor(EntityData.class));

        contextBuilder.add(BuffedHealthUpdateSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(BuffedInitiativeUpdateSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(BuffedMovePointsUpdateSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(BuffedActionPointsUpdateSystem.class.getConstructor(EntityData.class));

        contextBuilder.add(ResetApSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(ResetMpSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(ResetHealthSystem.class.getConstructor(EntityData.class));

        contextBuilder.add(MaxHealthEnforceSystem.class.getConstructor(EntityData.class));

        contextBuilder.add(TriggeredCleanupSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(CleanupSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(CascadeCleanupSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(PostResolveTriggerSystem.class.getConstructor(EntityData.class));
        return contextBuilder.build();
    }
    
    public TemplateImporter createImporter() throws NoSuchMethodException {
        TemplateImporter importer = new TemplateImporter();
        
        importer.register(AdditiveActionPoints.class.getConstructor(int.class));
        importer.register(MinRoundNumberCondition.class.getConstructor(int.class));
        importer.register(CascadeCleanup.class.getConstructor(EntityId.class));
        importer.register(CleanupEffect.class.getConstructor());
        importer.register(HealEffect.class.getConstructor(int.class));
        importer.register(MovePointsCost.class.getConstructor(int.class));
        importer.register(HasTriggerCost.class.getConstructor(EntityId.class));
        importer.register(FixedEffectTargets.class.getConstructor(EntityId[].class));
        importer.register(TargetActiveTurnCondition.class.getConstructor());
        importer.register(MoveEffect.class.getConstructor());
        importer.register(EndTurnEffect.class.getConstructor());
        importer.register(ResetMpEffect.class.getConstructor());
        importer.register(ResetApEffect.class.getConstructor());
        importer.register(ActivePlayerEffectTarget.class.getConstructor());
        importer.register(EndTurnTrigger.class.getConstructor());
        importer.register(PostResolveTriggerRequest.class.getConstructor(EntityId.class));
        importer.register(StartGameEffect.class.getConstructor());
        importer.register(Name.class.getConstructor(String.class));
        importer.register(Actor.class.getConstructor());
        importer.register(GameState.class.getConstructor());
        importer.register(Position.class.getConstructor(int.class, int.class));
        importer.register(BaseInitiative.class.getConstructor(int.class));
        importer.register(BaseHealth.class.getConstructor(int.class));
        importer.register(BaseActionPoints.class.getConstructor(int.class));
        importer.register(BaseMovePoints.class.getConstructor(int.class));
        return importer;
    }
}
