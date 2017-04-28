package com.etherdungeons.engine.abilities.effects;

import com.etherdungeons.engine.core.OwnedBy;
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
        data.streamEntities(Effect.class, Heal.class, OwnedBy.class, Target.class)
                .filter(e -> data.has(data.get(e, OwnedBy.class).getOwner(), Triggered.class))
                .forEach(effect -> {
            EntityId target = data.get(effect, Target.class).getTarget();
            ActiveHealth health = data.get(target, ActiveHealth.class);
            int hp = health != null? health.getHealth(): 0;
            hp += data.get(effect, Heal.class).getHeal();
            data.set(target, new ActiveHealth(hp));
        });
    }
}
