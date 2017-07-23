package com.etherdungeons.basemod.templates;

import com.etherdungeons.basemod.data.core.Name;
import com.etherdungeons.basemod.data.gameflow.Actor;
import com.etherdungeons.basemod.data.position.Position;
import com.etherdungeons.basemod.data.stats.base.BaseActionPoints;
import com.etherdungeons.basemod.data.stats.base.BaseHealth;
import com.etherdungeons.basemod.data.stats.base.BaseInitiative;
import com.etherdungeons.basemod.data.stats.base.BaseMovePoints;
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
public class CharacterTemplates implements TemplateProvider {

    @Override
    public Map<String, Template> getTemplates() {
        Map<String, Template> templates = new HashMap<>();
        templates.put("amara", CharacterTemplates::amara);
        templates.put("gobball", CharacterTemplates::gobball);
        
//        "amara": {
//        "character": ["#0", "Amara", 10, 11, 6, 3],
//        "endturnability": ["#0", "#1", "AmaraEndTurnAbility"],
//        "moveability": ["#0", "#2", "AmaraMoveAbility"],
//        "spiritclawability": ["#0", "#3", "AmaraSpiritClawAbility"],
//        "regenerationeffect": ["#0", "#4", "AmaraRegenerationAbility", 1]
//    },
//    "robert": {
//        "character": ["#0", "Robert", 9, 15, 4, 2],
//        "endturnability": ["#0", "#1", "RobertEndTurnAbility"],
//        "moveability": ["#0", "#2", "RobertMoveAbility"]
//    },
//    "character": {
//        "#0": {
//            "Name": ["#1"],
//            "Actor": []
//        },
//        "basestats": ["#0", "#2", "#3", "#4", "#5"]
//    },
//    "basestats": {
//        "#0": {
//            "BaseInitiative": ["#1"],
//            "BaseHealth": ["#2"],
//            "BaseActionPoints": ["#3"],
//            "BaseMovePoints": ["#4"]
//        }
//    }
        
        return templates;
    }

    static void amara(EntityData data, Object... params) {
        EntityId amara = (EntityId)params[0];
        actor(data, amara, "Amara");
        baseStats(data, amara, 10, 11, 6, 3);
        AbilityTemplates.endTurnAbility(data, amara);
        AbilityTemplates.moveAbility(data, amara);
        AbilityTemplates.spiritClawAbility(data, amara);
    }

    static void gobball(EntityData data, Object... params) {
        EntityId gobball = (EntityId)params[0];
        actor(data, gobball, "Gobball");
        baseStats(data, gobball, 3, 7, 4, 3);
        AbilityTemplates.endTurnAbility(data, gobball);
        AbilityTemplates.moveAbility(data, gobball);
    }

    static void actor(EntityData data, EntityId actor, String name) {
        data.set(actor, new Name(name));
        data.set(actor, new Actor());
    }

    static void baseStats(EntityData data, Object... params) {
        baseStats(data, (EntityId)params[0], (int)params[1], (int)params[2], (int)params[3], (int)params[4]);
    }

    static void baseStats(EntityData data, EntityId target, int initiative, int health, int actionPoints, int movePoints) {
        data.set(target, new BaseInitiative(initiative));
        data.set(target, new BaseHealth(health));
        data.set(target, new BaseActionPoints(actionPoints));
        data.set(target, new BaseMovePoints(movePoints));
    }

}
