package com.etherdungeons.engine.effects.heal;

import com.etherdungeons.engine.core.Target;
import com.etherdungeons.engine.gameflow.triggers.Triggered;
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
        for (EntityId triggered : data.entities(Triggered.class)) {
            EntityId effect = data.get(triggered, Triggered.class).getTrigger();
            if (data.hasAll(effect, HealEffect.class, Target.class)) {
                EntityId target = data.get(effect, Target.class).getTarget();
                ActiveHealth health = data.get(target, ActiveHealth.class);
                int hp = health != null ? health.getHealth() : 0;
                hp += data.get(effect, HealEffect.class).getHeal();
                data.set(target, new ActiveHealth(hp));
            }
        }
    }
}
