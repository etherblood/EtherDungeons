package com.etherdungeons.basemod.data.gameflow.effects.heal;

import com.etherdungeons.basemod.GameSystem;
import com.etherdungeons.basemod.data.gameflow.triggers.Triggered;
import com.etherdungeons.basemod.data.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.basemod.data.stats.active.ActiveHealth;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class HealSystem implements GameSystem {

    @Override
    public void run(EntityData data) {
        for (EntityId triggerArgs : data.entities(Triggered.class, TriggerArgsTargets.class)) {
            EntityId effect = data.get(triggerArgs, Triggered.class).getTrigger();
            if (data.has(effect, HealEffect.class)) {
                for (EntityId target : data.get(triggerArgs, TriggerArgsTargets.class).getTargets()) {
                    ActiveHealth health = data.get(target, ActiveHealth.class);
                    if(health != null) {
                        int hp = health.getHealth();
                        hp += data.get(effect, HealEffect.class).getHeal();
                        data.set(target, new ActiveHealth(hp));
                    }
                }
            }
        }
    }
}
