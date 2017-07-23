package com.etherdungeons.basemod.templates;

import com.etherdungeons.basemod.data.core.Name;
import com.etherdungeons.basemod.data.gameflow.effects.Ability;
import com.etherdungeons.basemod.data.gameflow.effects.move.MoveEffect;
import com.etherdungeons.basemod.data.gameflow.effects.spiritclaw.SpiritClawEffect;
import com.etherdungeons.basemod.data.gameflow.effects.targeting.AbilityOwnerTarget;
import com.etherdungeons.basemod.data.gameflow.effects.targeting.TriggerArgsPositionTarget;
import com.etherdungeons.basemod.data.gameflow.effects.turnflow.EndTurnEffect;
import com.etherdungeons.basemod.data.gameflow.triggers.cost.HasTriggerCost;
import com.etherdungeons.basemod.data.stats.cost.ActionPointsCost;
import com.etherdungeons.basemod.data.stats.cost.MovePointsCost;
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
public class AbilityTemplates implements TemplateProvider {

    @Override
    public Map<String, Template> getTemplates() {
        Map<String, Template> templates = new HashMap<>();
//        templates.put("amara", CharacterTemplates::amara);
        return templates;
    }
    
    static void endTurnAbility(EntityData data, EntityId abilityOwner) {
        EntityId ability = data.createEntity();
        ability(data, ability, abilityOwner, "EndTurnAbility");
        data.set(ability, new EndTurnEffect());
    }
    
    static void moveAbility(EntityData data, EntityId abilityOwner) {
        EntityId ability = data.createEntity();
        ability(data, ability, abilityOwner, "MoveAbility");
        data.set(ability, new MoveEffect());
        data.set(ability, new HasTriggerCost(abilityOwner));
        data.set(ability, new MovePointsCost(1));
        data.set(ability, new AbilityOwnerTarget());
    }
    
    static void spiritClawAbility(EntityData data, EntityId abilityOwner) {
        EntityId ability = data.createEntity();
        ability(data, ability, abilityOwner, "SpiritClawAbility");
        data.set(ability, new SpiritClawEffect());
        data.set(ability, new HasTriggerCost(abilityOwner));
        data.set(ability, new ActionPointsCost(4));
        data.set(ability, new TriggerArgsPositionTarget());
    }
    
    static void ability(EntityData data, EntityId ability, EntityId abilityOwner, String abilityName) {
        data.set(ability, new Ability(abilityOwner));
        data.set(ability, new Name(abilityName));
        Name ownerName = data.get(abilityOwner, Name.class);
        if(ownerName != null) {
            data.set(ability, new Name(ownerName.getName() + abilityName));
        } else {
            data.set(ability, new Name(abilityName));
        }
    }
    
//    "regenerationeffect": {
//        "#1": {
//            "Name": ["#2"],
//            "EndTurnTrigger": [],
//            "TargetActiveTurnCondition": [],
//            "HealEffect": ["#3"],
//            "FixedEffectTargets": [["#0"]]
//        }
//    }
}
