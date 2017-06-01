package com.etherdungeons.engine.sandbox;

import com.etherdungeons.context.Context;
import com.etherdungeons.context.ContextBuilder;
import com.etherdungeons.engine.actions.ActionManager;
import com.etherdungeons.engine.actions.implementations.EndTurnGenerator;
import com.etherdungeons.engine.actions.implementations.MoveGenerator;
import com.etherdungeons.engine.actions.implementations.SpiritClawGenerator;
import com.etherdungeons.engine.data.core.Name;
import com.etherdungeons.engine.data.gameflow.Actor;
import com.etherdungeons.engine.data.gameflow.GameState;
import com.etherdungeons.engine.data.gameflow.effects.Ability;
import com.etherdungeons.engine.data.gameflow.effects.cleanup.CascadeCleanup;
import com.etherdungeons.engine.data.gameflow.effects.cleanup.CascadeCleanupSystem;
import com.etherdungeons.engine.data.gameflow.effects.cleanup.CleanupEffect;
import com.etherdungeons.engine.data.gameflow.effects.cleanup.CleanupSystem;
import com.etherdungeons.engine.data.gameflow.effects.damage.DamageSystem;
import com.etherdungeons.engine.data.gameflow.effects.heal.HealEffect;
import com.etherdungeons.engine.data.gameflow.effects.heal.HealSystem;
import com.etherdungeons.engine.data.gameflow.effects.move.MoveEffect;
import com.etherdungeons.engine.data.gameflow.effects.move.MoveSystem;
import com.etherdungeons.engine.data.gameflow.effects.spiritclaw.SpiritClawEffect;
import com.etherdungeons.engine.data.gameflow.effects.spiritclaw.SpiritClawSystem;
import com.etherdungeons.engine.data.gameflow.effects.targeting.AbilityOwnerTarget;
import com.etherdungeons.engine.data.gameflow.effects.targeting.AbilityOwnerTargetSystem;
import com.etherdungeons.engine.data.gameflow.effects.targeting.ActivePlayerEffectTarget;
import com.etherdungeons.engine.data.gameflow.effects.targeting.ActivePlayerEffectTargetSystem;
import com.etherdungeons.engine.data.gameflow.effects.targeting.FixedEffectTargets;
import com.etherdungeons.engine.data.gameflow.effects.targeting.FixedEffectTargetsSystem;
import com.etherdungeons.engine.data.gameflow.effects.targeting.TriggerArgsPositionTarget;
import com.etherdungeons.engine.data.gameflow.effects.targeting.TriggerArgsPositionTargetSystem;
import com.etherdungeons.engine.data.gameflow.effects.turnflow.EndRoundSystem;
import com.etherdungeons.engine.data.gameflow.effects.turnflow.EndTurnEffect;
import com.etherdungeons.engine.data.gameflow.effects.turnflow.EndTurnSystem;
import com.etherdungeons.engine.data.gameflow.effects.turnflow.NextTurnSystem;
import com.etherdungeons.engine.data.gameflow.effects.turnflow.StartGameEffect;
import com.etherdungeons.engine.data.gameflow.effects.turnflow.StartGameSystem;
import com.etherdungeons.engine.data.gameflow.effects.turnflow.StartRoundSystem;
import com.etherdungeons.engine.data.gameflow.effects.turnflow.StartTurnSystem;
import com.etherdungeons.engine.data.gameflow.triggers.EndTurnTrigger;
import com.etherdungeons.engine.data.gameflow.triggers.EndTurnTriggerSystem;
import com.etherdungeons.engine.data.gameflow.triggers.PostResolveTriggerRequest;
import com.etherdungeons.engine.data.gameflow.triggers.PostResolveTriggerSystem;
import com.etherdungeons.engine.data.gameflow.triggers.StartTurnTriggerSystem;
import com.etherdungeons.engine.data.gameflow.triggers.TriggerRejectedSystem;
import com.etherdungeons.engine.data.gameflow.triggers.TriggerSystem;
import com.etherdungeons.engine.data.gameflow.triggers.TriggeredCleanupSystem;
import com.etherdungeons.engine.data.gameflow.triggers.conditions.ArgsMaxDistanceToTargetConditionSystem;
import com.etherdungeons.engine.data.gameflow.triggers.conditions.MinRoundNumberCondition;
import com.etherdungeons.engine.data.gameflow.triggers.conditions.MinRoundNumberConditionSystem;
import com.etherdungeons.engine.data.gameflow.triggers.conditions.TargetActiveTurnCondition;
import com.etherdungeons.engine.data.gameflow.triggers.conditions.TargetsActiveTurnConditionSystem;
import com.etherdungeons.engine.data.gameflow.triggers.cost.HasTriggerCost;
import com.etherdungeons.engine.data.gameflow.triggers.cost.PayTriggerCostSystem;
import com.etherdungeons.engine.data.position.Position;
import com.etherdungeons.engine.data.position.map.GameMap;
import com.etherdungeons.engine.data.stats.active.MaxHealthEnforceSystem;
import com.etherdungeons.engine.data.stats.active.reset.ResetApEffect;
import com.etherdungeons.engine.data.stats.active.reset.ResetApSystem;
import com.etherdungeons.engine.data.stats.active.reset.ResetHealthSystem;
import com.etherdungeons.engine.data.stats.active.reset.ResetMpEffect;
import com.etherdungeons.engine.data.stats.active.reset.ResetMpSystem;
import com.etherdungeons.engine.data.stats.additive.AdditiveActionPoints;
import com.etherdungeons.engine.data.stats.base.BaseActionPoints;
import com.etherdungeons.engine.data.stats.base.BaseHealth;
import com.etherdungeons.engine.data.stats.base.BaseInitiative;
import com.etherdungeons.engine.data.stats.base.BaseMovePoints;
import com.etherdungeons.engine.data.stats.buffed.BuffedActionPointsUpdateSystem;
import com.etherdungeons.engine.data.stats.buffed.BuffedHealthUpdateSystem;
import com.etherdungeons.engine.data.stats.buffed.BuffedInitiativeUpdateSystem;
import com.etherdungeons.engine.data.stats.buffed.BuffedMovePointsUpdateSystem;
import com.etherdungeons.engine.data.stats.cost.ActionPointsCost;
import com.etherdungeons.engine.data.stats.cost.MovePointsCost;
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
        contextBuilder.add(ActionManager.class.getConstructor(Collection.class));
        contextBuilder.add(MoveGenerator.class.getConstructor(EntityData.class));
        contextBuilder.add(EndTurnGenerator.class.getConstructor(EntityData.class));
        contextBuilder.add(SpiritClawGenerator.class.getConstructor(EntityData.class, GameMap.class));

        contextBuilder.add(FixedEffectTargetsSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(AbilityOwnerTargetSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(ActivePlayerEffectTargetSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(TriggerArgsPositionTargetSystem.class.getConstructor(EntityData.class, GameMap.class));

        contextBuilder.add(TargetsActiveTurnConditionSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(ArgsMaxDistanceToTargetConditionSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(MinRoundNumberConditionSystem.class.getConstructor(EntityData.class));

        contextBuilder.add(PayTriggerCostSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(TriggerRejectedSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(TriggerSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(StartTurnTriggerSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(EndTurnTriggerSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(SpiritClawSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(MoveSystem.class.getConstructor(EntityData.class, GameMap.class));
        contextBuilder.add(EndTurnSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(NextTurnSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(StartTurnSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(EndRoundSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(StartRoundSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(StartGameSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(HealSystem.class.getConstructor(EntityData.class));
        contextBuilder.add(DamageSystem.class.getConstructor(EntityData.class));

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

        importer.registerComponent(AdditiveActionPoints.class.getConstructor(int.class));
        importer.registerComponent(MinRoundNumberCondition.class.getConstructor(int.class));
        importer.registerComponent(CascadeCleanup.class.getConstructor(EntityId.class));
        importer.registerComponent(CleanupEffect.class.getConstructor());
        importer.registerComponent(HealEffect.class.getConstructor(int.class));
        importer.registerComponent(MovePointsCost.class.getConstructor(int.class));
        importer.registerComponent(ActionPointsCost.class.getConstructor(int.class));
        importer.registerComponent(HasTriggerCost.class.getConstructor(EntityId.class));
        importer.registerComponent(FixedEffectTargets.class.getConstructor(EntityId[].class));
        importer.registerComponent(TargetActiveTurnCondition.class.getConstructor());
        importer.registerComponent(MoveEffect.class.getConstructor());
        importer.registerComponent(EndTurnEffect.class.getConstructor());
        importer.registerComponent(ResetMpEffect.class.getConstructor());
        importer.registerComponent(ResetApEffect.class.getConstructor());
        importer.registerComponent(ActivePlayerEffectTarget.class.getConstructor());
        importer.registerComponent(EndTurnTrigger.class.getConstructor());
        importer.registerComponent(PostResolveTriggerRequest.class.getConstructor(EntityId.class));
        importer.registerComponent(StartGameEffect.class.getConstructor());
        importer.registerComponent(Name.class.getConstructor(String.class));
        importer.registerComponent(Actor.class.getConstructor());
        importer.registerComponent(GameState.class.getConstructor());
        importer.registerComponent(Position.class.getConstructor(int.class, int.class));
        importer.registerComponent(BaseInitiative.class.getConstructor(int.class));
        importer.registerComponent(BaseHealth.class.getConstructor(int.class));
        importer.registerComponent(BaseActionPoints.class.getConstructor(int.class));
        importer.registerComponent(BaseMovePoints.class.getConstructor(int.class));
        importer.registerComponent(Ability.class.getConstructor(EntityId.class));
        importer.registerComponent(AbilityOwnerTarget.class.getConstructor());
        importer.registerComponent(SpiritClawEffect.class.getConstructor());
        importer.registerComponent(TriggerArgsPositionTarget.class.getConstructor());
        return importer;
    }
    
}
