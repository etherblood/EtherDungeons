package com.etherdungeons.engine.stats.buffed;

import com.etherdungeons.engine.stats.additive.AdditiveStatsTarget;
import com.etherdungeons.entitysystem.EntityComponent;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Philipp
 */
public abstract class AbstractBuffedStatUpdateSystem<Base extends EntityComponent, Additive extends EntityComponent, Buffed extends EntityComponent> implements Runnable {

    protected final EntityData data;

    public AbstractBuffedStatUpdateSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
        data.removeAllComponents(getBuffedStatClass());
        Map<EntityId, Integer> additiveStat = new HashMap<>();
        for (EntityId buff : data.entities(getAdditiveStatClass(), AdditiveStatsTarget.class)) {
            EntityId target = data.get(buff, AdditiveStatsTarget.class).getTarget();
            int stat = additiveStat.getOrDefault(target, 0);
            stat += getAdditiveStatValue(data.get(buff, getAdditiveStatClass()));
            additiveStat.put(target, stat);
        }
        for (EntityId entity : data.entities(getBaseStatClass())) {
            int stat = additiveStat.getOrDefault(entity, 0);
            stat += getBaseStatValue(data.get(entity, getBaseStatClass()));
            data.set(entity, createBuffedStat(stat));
        }
    }
    
    protected abstract Class<Base> getBaseStatClass();
    protected abstract Class<Additive> getAdditiveStatClass();
    protected abstract Class<Buffed> getBuffedStatClass();
    
    protected abstract int getBaseStatValue(Base baseStat);
    protected abstract int getAdditiveStatValue(Additive additiveStat);
    protected abstract Buffed createBuffedStat(int buffedStatValue);
}
