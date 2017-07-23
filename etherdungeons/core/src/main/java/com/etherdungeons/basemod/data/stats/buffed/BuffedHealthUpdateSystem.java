package com.etherdungeons.basemod.data.stats.buffed;

import com.etherdungeons.basemod.data.stats.additive.AdditiveHealth;
import com.etherdungeons.basemod.data.stats.base.BaseHealth;

/**
 *
 * @author Philipp
 */
public class BuffedHealthUpdateSystem extends AbstractBuffedStatUpdateSystem<BaseHealth, AdditiveHealth, BuffedHealth>{

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
