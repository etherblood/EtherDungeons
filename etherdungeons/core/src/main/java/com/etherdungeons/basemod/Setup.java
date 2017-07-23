package com.etherdungeons.basemod;

import com.etherdungeons.basemod.mods.BaseMod;
import com.etherdungeons.basemod.data.core.Name;
import com.etherdungeons.basemod.data.gameflow.Actor;
import com.etherdungeons.basemod.data.gameflow.GameState;
import com.etherdungeons.basemod.data.gameflow.MapSize;
import com.etherdungeons.basemod.data.gameflow.effects.Ability;
import com.etherdungeons.basemod.data.gameflow.effects.cleanup.CascadeCleanup;
import com.etherdungeons.basemod.data.gameflow.effects.cleanup.CleanupEffect;
import com.etherdungeons.basemod.data.gameflow.effects.heal.HealEffect;
import com.etherdungeons.basemod.data.gameflow.effects.move.MoveEffect;
import com.etherdungeons.basemod.data.gameflow.effects.spiritclaw.SpiritClawEffect;
import com.etherdungeons.basemod.data.gameflow.effects.targeting.AbilityOwnerTarget;
import com.etherdungeons.basemod.data.gameflow.effects.targeting.ActivePlayerEffectTarget;
import com.etherdungeons.basemod.data.gameflow.effects.targeting.FixedEffectTargets;
import com.etherdungeons.basemod.data.gameflow.effects.targeting.TriggerArgsPositionTarget;
import com.etherdungeons.basemod.data.gameflow.effects.turnflow.EndTurnEffect;
import com.etherdungeons.basemod.data.gameflow.effects.turnflow.StartGameEffect;
import com.etherdungeons.basemod.data.gameflow.triggers.EndTurnTrigger;
import com.etherdungeons.basemod.data.gameflow.triggers.PostResolveTriggerRequest;
import com.etherdungeons.basemod.data.gameflow.triggers.TriggerRequest;
import com.etherdungeons.basemod.data.gameflow.triggers.conditions.MinRoundNumberCondition;
import com.etherdungeons.basemod.data.gameflow.triggers.conditions.TargetActiveTurnCondition;
import com.etherdungeons.basemod.data.gameflow.triggers.cost.HasTriggerCost;
import com.etherdungeons.basemod.data.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.basemod.data.position.Position;
import com.etherdungeons.basemod.data.stats.active.reset.ResetApEffect;
import com.etherdungeons.basemod.data.stats.active.reset.ResetMpEffect;
import com.etherdungeons.basemod.data.stats.additive.AdditiveActionPoints;
import com.etherdungeons.basemod.data.stats.base.BaseActionPoints;
import com.etherdungeons.basemod.data.stats.base.BaseHealth;
import com.etherdungeons.basemod.data.stats.base.BaseInitiative;
import com.etherdungeons.basemod.data.stats.base.BaseMovePoints;
import com.etherdungeons.basemod.data.stats.cost.ActionPointsCost;
import com.etherdungeons.basemod.data.stats.cost.MovePointsCost;
import com.etherdungeons.basemod.mods.SimpleLoggerMod;
import com.etherdungeons.context.Context;
import com.etherdungeons.entitysystem.EntityId;
import com.etherdungeons.modding.ModCombiner;
import com.etherdungeons.templates.json.JsonTemplateImporter;

/**
 *
 * @author Philipp
 */
public class Setup {

    public Context createContext() {
        return new ModCombiner(new BaseMod(), new SimpleLoggerMod()).build();
    }

    public JsonTemplateImporter createImporter() {
        try {
            return createImporterThrows();
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
    }

    private JsonTemplateImporter createImporterThrows() throws NoSuchMethodException, SecurityException {
        JsonTemplateImporter importer = new JsonTemplateImporter();
        
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
        importer.registerComponent(MapSize.class.getConstructor(int.class, int.class));
        importer.registerComponent(BaseInitiative.class.getConstructor(int.class));
        importer.registerComponent(BaseHealth.class.getConstructor(int.class));
        importer.registerComponent(BaseActionPoints.class.getConstructor(int.class));
        importer.registerComponent(BaseMovePoints.class.getConstructor(int.class));
        importer.registerComponent(Ability.class.getConstructor(EntityId.class));
        importer.registerComponent(AbilityOwnerTarget.class.getConstructor());
        importer.registerComponent(SpiritClawEffect.class.getConstructor());
        importer.registerComponent(TriggerArgsPositionTarget.class.getConstructor());
        importer.registerComponent(TriggerArgsTargets.class.getConstructor(EntityId[].class));
        importer.registerComponent(TriggerRequest.class.getConstructor(EntityId.class));
        return importer;
    }
    
}
