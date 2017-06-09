package com.etherdungeons.basemod;

import com.etherdungeons.context.definitions.BeanDefinitionFactory;
import com.etherdungeons.basemod.actions.ActionManager;
import com.etherdungeons.basemod.actions.implementations.EndTurnGenerator;
import com.etherdungeons.basemod.actions.implementations.MoveGenerator;
import com.etherdungeons.basemod.actions.implementations.SpiritClawGenerator;
import com.etherdungeons.basemod.data.gameflow.effects.cleanup.CascadeCleanupSystem;
import com.etherdungeons.basemod.data.gameflow.effects.cleanup.CleanupSystem;
import com.etherdungeons.basemod.data.gameflow.effects.damage.DamageSystem;
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
import com.etherdungeons.basemod.data.position.map.GameMap;
import com.etherdungeons.basemod.data.position.map.MapInitSystem;
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
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityDataImpl;
import com.etherdungeons.modding.Mod;
import com.etherdungeons.modding.SystemDefinition;
import com.etherdungeons.templates.TemplateService;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class BaseMod implements Mod {
    
    public final static int ORDER_DEFAULT = 0;
    public final static int ORDER_TARGETING = 10000;
    public final static int ORDER_CONDITION = 20000;
    public final static int ORDER_TRIGGER_COST = 30000;
    public final static int ORDER_TRIGGER = 40000;
    public final static int ORDER_STATS_UPDATE = 50000;
    public final static int ORDER_POST = 60000;

    private final List<SystemDefinition> list = new ArrayList<>();

    public BaseMod() {
        try {
            init();
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void init() throws NoSuchMethodException {
        add(ORDER_DEFAULT, EntityDataImpl.class.getConstructor());
        add(ORDER_DEFAULT, GameMap.class.getConstructor());
        add(ORDER_DEFAULT, ActionManager.class.getConstructor(Collection.class));
        add(ORDER_DEFAULT, MoveGenerator.class.getConstructor(EntityData.class));
        add(ORDER_DEFAULT, EndTurnGenerator.class.getConstructor(EntityData.class));
        add(ORDER_DEFAULT, SpiritClawGenerator.class.getConstructor(EntityData.class, GameMap.class));
        
        add(ORDER_DEFAULT, TemplateService.class.getConstructor(Collection.class));
        add(ORDER_DEFAULT, AbilityTemplates.class.getConstructor());
        add(ORDER_DEFAULT, CharacterTemplates.class.getConstructor());
        add(ORDER_DEFAULT, MapTemplates.class.getConstructor());

        add(ORDER_TARGETING, FixedEffectTargetsSystem.class.getConstructor(EntityData.class));
        add(ORDER_TARGETING, AbilityOwnerTargetSystem.class.getConstructor(EntityData.class));
        add(ORDER_TARGETING, ActivePlayerEffectTargetSystem.class.getConstructor(EntityData.class));
        add(ORDER_TARGETING, TriggerArgsPositionTargetSystem.class.getConstructor(EntityData.class, GameMap.class));

        add(ORDER_CONDITION, TargetsActiveTurnConditionSystem.class.getConstructor(EntityData.class));
        add(ORDER_CONDITION, ArgsMaxDistanceToTargetConditionSystem.class.getConstructor(EntityData.class));
        add(ORDER_CONDITION, MinRoundNumberConditionSystem.class.getConstructor(EntityData.class));

        add(ORDER_TRIGGER_COST, PayTriggerCostSystem.class.getConstructor(EntityData.class));
        add(ORDER_TRIGGER, TriggerRejectedSystem.class.getConstructor(EntityData.class));
        add(ORDER_TRIGGER, TriggerSystem.class.getConstructor(EntityData.class));
        add(ORDER_TRIGGER, StartGameSystem.class.getConstructor(EntityData.class));
        add(ORDER_TRIGGER, MapInitSystem.class.getConstructor(EntityData.class, GameMap.class));
        add(ORDER_TRIGGER, StartTurnTriggerSystem.class.getConstructor(EntityData.class));
        add(ORDER_TRIGGER, EndTurnTriggerSystem.class.getConstructor(EntityData.class));
        add(ORDER_TRIGGER, SpiritClawSystem.class.getConstructor(EntityData.class));
        add(ORDER_TRIGGER, MoveSystem.class.getConstructor(EntityData.class, GameMap.class));
        add(ORDER_TRIGGER, EndTurnSystem.class.getConstructor(EntityData.class));
        add(ORDER_TRIGGER, NextTurnSystem.class.getConstructor(EntityData.class));
        add(ORDER_TRIGGER, StartTurnSystem.class.getConstructor(EntityData.class));
        add(ORDER_TRIGGER, EndRoundSystem.class.getConstructor(EntityData.class));
        add(ORDER_TRIGGER, StartRoundSystem.class.getConstructor(EntityData.class));
        add(ORDER_TRIGGER, HealSystem.class.getConstructor(EntityData.class));
        add(ORDER_TRIGGER, DamageSystem.class.getConstructor(EntityData.class));

        add(ORDER_STATS_UPDATE, BuffedHealthUpdateSystem.class.getConstructor(EntityData.class));
        add(ORDER_STATS_UPDATE, BuffedInitiativeUpdateSystem.class.getConstructor(EntityData.class));
        add(ORDER_STATS_UPDATE, BuffedMovePointsUpdateSystem.class.getConstructor(EntityData.class));
        add(ORDER_STATS_UPDATE, BuffedActionPointsUpdateSystem.class.getConstructor(EntityData.class));

        add(ORDER_STATS_UPDATE, ResetApSystem.class.getConstructor(EntityData.class));
        add(ORDER_STATS_UPDATE, ResetMpSystem.class.getConstructor(EntityData.class));
        add(ORDER_STATS_UPDATE, ResetHealthSystem.class.getConstructor(EntityData.class));

        add(ORDER_STATS_UPDATE, MaxHealthEnforceSystem.class.getConstructor(EntityData.class));

        add(ORDER_POST, TriggeredCleanupSystem.class.getConstructor(EntityData.class));
        add(ORDER_POST, CleanupSystem.class.getConstructor(EntityData.class));
        add(ORDER_POST, CascadeCleanupSystem.class.getConstructor(EntityData.class));
        add(ORDER_POST, PostResolveTriggerSystem.class.getConstructor(EntityData.class));
    }
    
    private void add(int priority, Constructor<?> constructor) {
        list.add(new SystemDefinition(BeanDefinitionFactory.of(constructor), priority));
    }

    @Override
    public List<SystemDefinition> getSystemDefinitions() {
        return list;
    }
}
