package com.etherdungeons.basemod.mods;

import com.etherdungeons.basemod.GameController;
import com.etherdungeons.context.definitions.BeanDefinitionFactory;
import com.etherdungeons.basemod.actions.ActionManager;
import com.etherdungeons.basemod.actions.implementations.EndTurnGenerator;
import com.etherdungeons.basemod.actions.implementations.MoveGenerator;
import com.etherdungeons.basemod.actions.implementations.SpiritClawGenerator;
import com.etherdungeons.basemod.data.gameflow.effects.cleanup.CascadeCleanupSystem;
import com.etherdungeons.basemod.data.gameflow.effects.cleanup.CleanupSystem;
import com.etherdungeons.basemod.data.gameflow.effects.damage.DamageSystem;
import com.etherdungeons.basemod.data.gameflow.effects.death.DeathSystem;
import com.etherdungeons.basemod.data.gameflow.effects.death.NoHealthDeathSystem;
import com.etherdungeons.basemod.data.gameflow.effects.heal.HealSystem;
import com.etherdungeons.basemod.data.gameflow.effects.move.MoveSystem;
import com.etherdungeons.basemod.data.gameflow.effects.spiritclaw.SpiritClawSystem;
import com.etherdungeons.basemod.data.gameflow.effects.targeting.AbilityOwnerTargetSystem;
import com.etherdungeons.basemod.data.gameflow.effects.targeting.ActivePlayerEffectTargetSystem;
import com.etherdungeons.basemod.data.gameflow.effects.targeting.FixedEffectTargetsSystem;
import com.etherdungeons.basemod.data.gameflow.effects.targeting.TriggerArgsPositionTargetSystem;
import com.etherdungeons.basemod.data.gameflow.effects.turnflow.EndRoundSystem;
import com.etherdungeons.basemod.data.gameflow.effects.turnflow.EndTurnSystem;
import com.etherdungeons.basemod.data.gameflow.effects.turnflow.NextTurnSystem;
import com.etherdungeons.basemod.data.gameflow.effects.turnflow.StartGameSystem;
import com.etherdungeons.basemod.data.gameflow.effects.turnflow.StartRoundSystem;
import com.etherdungeons.basemod.data.gameflow.effects.turnflow.StartTurnSystem;
import com.etherdungeons.basemod.data.gameflow.triggers.EndTurnTriggerSystem;
import com.etherdungeons.basemod.data.gameflow.triggers.PostResolveTriggerSystem;
import com.etherdungeons.basemod.data.gameflow.triggers.StartTurnTriggerSystem;
import com.etherdungeons.basemod.data.gameflow.triggers.TriggerRejectedSystem;
import com.etherdungeons.basemod.data.gameflow.triggers.TriggerSystem;
import com.etherdungeons.basemod.data.gameflow.triggers.TriggeredCleanupSystem;
import com.etherdungeons.basemod.data.gameflow.triggers.conditions.ArgsMaxDistanceToTargetConditionSystem;
import com.etherdungeons.basemod.data.gameflow.triggers.conditions.MinRoundNumberConditionSystem;
import com.etherdungeons.basemod.data.gameflow.triggers.conditions.TargetsActiveTurnConditionSystem;
import com.etherdungeons.basemod.data.gameflow.triggers.cost.PayTriggerCostSystem;
import com.etherdungeons.basemod.data.stats.active.MaxHealthEnforceSystem;
import com.etherdungeons.basemod.data.stats.active.reset.ResetApSystem;
import com.etherdungeons.basemod.data.stats.active.reset.ResetHealthSystem;
import com.etherdungeons.basemod.data.stats.active.reset.ResetMpSystem;
import com.etherdungeons.basemod.data.stats.buffed.BuffedActionPointsUpdateSystem;
import com.etherdungeons.basemod.data.stats.buffed.BuffedHealthUpdateSystem;
import com.etherdungeons.basemod.data.stats.buffed.BuffedInitiativeUpdateSystem;
import com.etherdungeons.basemod.data.stats.buffed.BuffedMovePointsUpdateSystem;
import com.etherdungeons.basemod.templates.AbilityTemplates;
import com.etherdungeons.basemod.templates.CharacterTemplates;
import com.etherdungeons.basemod.templates.MapTemplates;
import com.etherdungeons.context.definitions.BeanDefinition;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityDataImpl;
import com.etherdungeons.modding.Mod;
import com.etherdungeons.modding.OrderConstraint;
import com.etherdungeons.templates.TemplateService;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.slf4j.ILoggerFactory;

/**
 *
 * @author Philipp
 */
public class BaseMod extends AbstractMod {
    
    public final static OrderConstraint ORDER_DEFAULT = new OrderConstraint();//
    public final static OrderConstraint ORDER_TARGETING = new OrderConstraint();//10000;
    public final static OrderConstraint ORDER_CONDITION = new OrderConstraint();//20000;
    public final static OrderConstraint ORDER_TRIGGER_COST = new OrderConstraint();//30000;
    public final static OrderConstraint ORDER_TRIGGER = new OrderConstraint();//40000;
    public final static OrderConstraint ORDER_STATS_UPDATE = new OrderConstraint();//50000;
    public final static OrderConstraint ORDER_POST = new OrderConstraint();//60000;

    public BaseMod() {
        try {
            init();
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void init() throws NoSuchMethodException {
        defineOrder(ORDER_TARGETING, ORDER_CONDITION);
        defineOrder(ORDER_CONDITION, ORDER_TRIGGER_COST);
        defineOrder(ORDER_TRIGGER_COST, ORDER_TRIGGER);
        defineOrder(ORDER_TRIGGER, ORDER_STATS_UPDATE);
        defineOrder(ORDER_STATS_UPDATE, ORDER_POST);
        
        constraints.add(ORDER_DEFAULT);
        constraints.add(ORDER_TARGETING);
        constraints.add(ORDER_CONDITION);
        constraints.add(ORDER_TRIGGER_COST);
        constraints.add(ORDER_TRIGGER);
        constraints.add(ORDER_STATS_UPDATE);
        constraints.add(ORDER_POST);
        
        add(EntityDataImpl.class.getConstructor(), ORDER_DEFAULT);
        add(ActionManager.class.getConstructor(Collection.class, ILoggerFactory.class), ORDER_DEFAULT);
        add(MoveGenerator.class.getConstructor(EntityData.class), ORDER_DEFAULT);
        add(EndTurnGenerator.class.getConstructor(EntityData.class), ORDER_DEFAULT);
        add(SpiritClawGenerator.class.getConstructor(EntityData.class), ORDER_DEFAULT);
        add(GameController.class.getConstructor(EntityData.class, ActionManager.class, List.class, ILoggerFactory.class), ORDER_DEFAULT);
        
        add(TemplateService.class.getConstructor(Collection.class), ORDER_DEFAULT);
        add(AbilityTemplates.class.getConstructor(), ORDER_DEFAULT);
        add(CharacterTemplates.class.getConstructor(), ORDER_DEFAULT);
        add(MapTemplates.class.getConstructor(), ORDER_DEFAULT);

        add(FixedEffectTargetsSystem.class.getConstructor(), ORDER_TARGETING);
        add(AbilityOwnerTargetSystem.class.getConstructor(), ORDER_TARGETING);
        add(ActivePlayerEffectTargetSystem.class.getConstructor(), ORDER_TARGETING);
        add(TriggerArgsPositionTargetSystem.class.getConstructor(), ORDER_TARGETING);

        add(TargetsActiveTurnConditionSystem.class.getConstructor(), ORDER_CONDITION);
        add(ArgsMaxDistanceToTargetConditionSystem.class.getConstructor(), ORDER_CONDITION);
        add(MinRoundNumberConditionSystem.class.getConstructor(), ORDER_CONDITION);

        add(PayTriggerCostSystem.class.getConstructor(), ORDER_TRIGGER_COST);
        add(TriggerRejectedSystem.class.getConstructor(), ORDER_TRIGGER);
        add(TriggerSystem.class.getConstructor(), ORDER_TRIGGER);
        add(StartGameSystem.class.getConstructor(), ORDER_TRIGGER);
        add(StartTurnTriggerSystem.class.getConstructor(), ORDER_TRIGGER);
        add(EndTurnTriggerSystem.class.getConstructor(), ORDER_TRIGGER);
        add(SpiritClawSystem.class.getConstructor(), ORDER_TRIGGER);
        add(MoveSystem.class.getConstructor(), ORDER_TRIGGER);
        add(EndTurnSystem.class.getConstructor(), ORDER_TRIGGER);
        add(NextTurnSystem.class.getConstructor(), ORDER_TRIGGER);
        add(StartTurnSystem.class.getConstructor(), ORDER_TRIGGER);
        add(EndRoundSystem.class.getConstructor(), ORDER_TRIGGER);
        add(StartRoundSystem.class.getConstructor(), ORDER_TRIGGER);
        add(HealSystem.class.getConstructor(), ORDER_TRIGGER);
        add(DamageSystem.class.getConstructor(), ORDER_TRIGGER);

        add(CleanupSystem.class.getConstructor(), ORDER_TRIGGER);
        add(CascadeCleanupSystem.class.getConstructor(), ORDER_TRIGGER);

        add(BuffedHealthUpdateSystem.class.getConstructor(), ORDER_STATS_UPDATE);
        add(BuffedInitiativeUpdateSystem.class.getConstructor(), ORDER_STATS_UPDATE);
        add(BuffedMovePointsUpdateSystem.class.getConstructor(), ORDER_STATS_UPDATE);
        add(BuffedActionPointsUpdateSystem.class.getConstructor(), ORDER_STATS_UPDATE);

        add(ResetApSystem.class.getConstructor(), ORDER_STATS_UPDATE);
        add(ResetMpSystem.class.getConstructor(), ORDER_STATS_UPDATE);
        add(ResetHealthSystem.class.getConstructor(), ORDER_STATS_UPDATE);

        add(MaxHealthEnforceSystem.class.getConstructor(), ORDER_STATS_UPDATE);
        add(NoHealthDeathSystem.class.getConstructor(), ORDER_STATS_UPDATE);
        add(DeathSystem.class.getConstructor(), ORDER_STATS_UPDATE);
        
        add(TriggeredCleanupSystem.class.getConstructor(), ORDER_POST);
        add(PostResolveTriggerSystem.class.getConstructor(), ORDER_POST);
    }
}
