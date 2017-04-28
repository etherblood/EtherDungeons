package com.etherdungeons.engine.stats.buffed;

import com.etherdungeons.engine.core.Target;
import com.etherdungeons.engine.stats.additive.AdditiveActionPoints;
import com.etherdungeons.engine.stats.base.BaseActionPoints;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Philipp
 */
public class BuffedActionPointsUpdateSystem implements Runnable {

    private final EntityData data;

    public BuffedActionPointsUpdateSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
        for (EntityId entity : data.entities(BuffedActionPoints.class)) {
            data.remove(entity, BuffedActionPoints.class);
        }
        Map<EntityId, Integer> additionalActionPoints = new HashMap<>();
        for (EntityId entity : data.entities(AdditiveActionPoints.class, Target.class)) {
            int health = additionalActionPoints.getOrDefault(entity, 0);
            health += data.get(entity, AdditiveActionPoints.class).getAp();
            additionalActionPoints.put(entity, health);
        }
        for (EntityId entity : data.entities(BaseActionPoints.class)) {
            int health = additionalActionPoints.getOrDefault(entity, 0);
            health += data.get(entity, BaseActionPoints.class).getAp();
            data.set(entity, new BuffedActionPoints(health));
        }
    }
}
