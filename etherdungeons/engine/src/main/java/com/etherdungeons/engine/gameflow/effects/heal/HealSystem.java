package com.etherdungeons.engine.gameflow.effects.heal;

import com.etherdungeons.engine.gameflow.triggers.Triggered;
import com.etherdungeons.engine.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.engine.stats.active.ActiveHealth;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class HealSystem implements Runnable {

    private final EntityData data;

    public HealSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
        for (EntityId triggerArgs : data.entities(Triggered.class, TriggerArgsTargets.class)) {
            EntityId effect = data.get(triggerArgs, Triggered.class).getTrigger();
            if (data.has(effect, HealEffect.class)) {
                for (EntityId target : data.get(triggerArgs, TriggerArgsTargets.class).getTargets()) {
                    ActiveHealth health = data.get(target, ActiveHealth.class);
                    int hp = health != null ? health.getHealth() : 0;
                    hp += data.get(effect, HealEffect.class).getHeal();
                    data.set(target, new ActiveHealth(hp));
                }
            }
        }
    }
}
