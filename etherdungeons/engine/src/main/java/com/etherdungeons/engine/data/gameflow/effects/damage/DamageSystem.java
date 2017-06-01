package com.etherdungeons.engine.data.gameflow.effects.damage;

import com.etherdungeons.engine.data.gameflow.triggers.Triggered;
import com.etherdungeons.engine.data.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.engine.data.stats.active.ActiveHealth;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class DamageSystem implements Runnable {

    private final EntityData data;

    public DamageSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
        for (EntityId triggerArgs : data.entities(Triggered.class, TriggerArgsTargets.class)) {
            EntityId effect = data.get(triggerArgs, Triggered.class).getTrigger();
            if (data.has(effect, DamageEffect.class)) {
                for (EntityId target : data.get(triggerArgs, TriggerArgsTargets.class).getTargets()) {
                    ActiveHealth health = data.get(target, ActiveHealth.class);
                    if (health != null) {
                        int hp = health.getHealth();
                        hp -= data.get(effect, DamageEffect.class).getDamage();
                        data.set(target, new ActiveHealth(hp));
                    }
                }
            }
        }
    }
}
