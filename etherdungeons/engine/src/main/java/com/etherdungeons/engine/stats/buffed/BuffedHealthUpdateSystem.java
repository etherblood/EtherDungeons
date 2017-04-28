package com.etherdungeons.engine.stats.buffed;

import com.etherdungeons.engine.core.Target;
import com.etherdungeons.engine.stats.additive.AdditiveHealth;
import com.etherdungeons.engine.stats.base.BaseHealth;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Philipp
 */
public class BuffedHealthUpdateSystem implements Runnable {

    private final EntityData data;

    public BuffedHealthUpdateSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
        for (EntityId entity : data.entities(BuffedHealth.class)) {
            data.remove(entity, BuffedHealth.class);
        }
        Map<EntityId, Integer> additionalHealth = new HashMap<>();
        for (EntityId entity : data.entities(AdditiveHealth.class, Target.class)) {
            int health = additionalHealth.getOrDefault(entity, 0);
            health += data.get(entity, AdditiveHealth.class).getHealth();
            additionalHealth.put(entity, health);
        }
        for (EntityId entity : data.entities(BaseHealth.class)) {
            int health = additionalHealth.getOrDefault(entity, 0);
            health += data.get(entity, BaseHealth.class).getHealth();
            data.set(entity, new BuffedHealth(health));
        }
    }
}
