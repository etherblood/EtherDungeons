package com.etherdungeons.engine.stats.buffed;

import com.etherdungeons.engine.stats.additive.AdditiveHealth;
import com.etherdungeons.engine.stats.base.BaseHealth;
import com.etherdungeons.entitysystem.EntityData;

/**
 *
 * @author Philipp
 */
public class BuffedHealthUpdateSystem extends AbstractBuffedStatUpdateSystem<BaseHealth, AdditiveHealth, BuffedHealth>{

    public BuffedHealthUpdateSystem(EntityData data) {
        super(data);
    }

    @Override
    protected Class<BaseHealth> getBaseStatClass() {
        return BaseHealth.class;
    }

    @Override
    protected Class<AdditiveHealth> getAdditiveStatClass() {
        return AdditiveHealth.class;
    }

    @Override
    protected Class<BuffedHealth> getBuffedStatClass() {
        return BuffedHealth.class;
    }

    @Override
    protected int getBaseStatValue(BaseHealth baseStat) {
        return baseStat.getHealth();
    }

    @Override
    protected int getAdditiveStatValue(AdditiveHealth additiveStat) {
        return additiveStat.getHealth();
    }

    @Override
    protected BuffedHealth createBuffedStat(int buffedStatValue) {
        return new BuffedHealth(buffedStatValue);
    }
}
