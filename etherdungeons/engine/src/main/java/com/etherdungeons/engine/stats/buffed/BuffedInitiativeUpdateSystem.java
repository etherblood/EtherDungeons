package com.etherdungeons.engine.stats.buffed;

import com.etherdungeons.engine.core.Target;
import com.etherdungeons.engine.stats.additive.AdditiveInitiative;
import com.etherdungeons.engine.stats.base.BaseInitiative;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Philipp
 */
public class BuffedInitiativeUpdateSystem implements Runnable {

    private final EntityData data;

    public BuffedInitiativeUpdateSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
        for (EntityId entity : data.entities(BuffedInitiative.class)) {
            data.remove(entity, BuffedInitiative.class);
        }
        Map<EntityId, Integer> additionalInitiative = new HashMap<>();
        for (EntityId entity : data.entities(AdditiveInitiative.class, Target.class)) {
            int health = additionalInitiative.getOrDefault(entity, 0);
            health += data.get(entity, AdditiveInitiative.class).getInit();
            additionalInitiative.put(entity, health);
        }
        for (EntityId entity : data.entities(BaseInitiative.class)) {
            int health = additionalInitiative.getOrDefault(entity, 0);
            health += data.get(entity, BaseInitiative.class).getInit();
            data.set(entity, new BuffedInitiative(health));
        }
    }
}
