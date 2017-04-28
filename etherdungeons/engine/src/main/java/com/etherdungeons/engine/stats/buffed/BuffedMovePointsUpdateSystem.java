package com.etherdungeons.engine.stats.buffed;

import com.etherdungeons.engine.core.Target;
import com.etherdungeons.engine.stats.additive.AdditiveMovePoints;
import com.etherdungeons.engine.stats.base.BaseMovePoints;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Philipp
 */
public class BuffedMovePointsUpdateSystem implements Runnable {

    private final EntityData data;

    public BuffedMovePointsUpdateSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
        for (EntityId entity : data.entities(BuffedMovePoints.class)) {
            data.remove(entity, BuffedMovePoints.class);
        }
        Map<EntityId, Integer> additionalMovePoints = new HashMap<>();
        for (EntityId entity : data.entities(AdditiveMovePoints.class, Target.class)) {
            int health = additionalMovePoints.getOrDefault(entity, 0);
            health += data.get(entity, AdditiveMovePoints.class).getMp();
            additionalMovePoints.put(entity, health);
        }
        for (EntityId entity : data.entities(BaseMovePoints.class)) {
            int health = additionalMovePoints.getOrDefault(entity, 0);
            health += data.get(entity, BaseMovePoints.class).getMp();
            data.set(entity, new BuffedMovePoints(health));
        }
    }
}
