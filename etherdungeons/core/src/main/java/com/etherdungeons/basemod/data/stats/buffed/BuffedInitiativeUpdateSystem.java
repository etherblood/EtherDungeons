package com.etherdungeons.basemod.data.stats.buffed;

import com.etherdungeons.basemod.data.stats.additive.AdditiveInitiative;
import com.etherdungeons.basemod.data.stats.base.BaseInitiative;

/**
 *
 * @author Philipp
 */
public class BuffedInitiativeUpdateSystem extends AbstractBuffedStatUpdateSystem<BaseInitiative, AdditiveInitiative, BuffedInitiative>{

    @Override
    protected Class<BaseInitiative> getBaseStatClass() {
        return BaseInitiative.class;
    }

    @Override
    protected Class<AdditiveInitiative> getAdditiveStatClass() {
        return AdditiveInitiative.class;
    }

    @Override
    protected Class<BuffedInitiative> getBuffedStatClass() {
        return BuffedInitiative.class;
    }

    @Override
    protected int getBaseStatValue(BaseInitiative baseStat) {
        return baseStat.getInit();
    }

    @Override
    protected int getAdditiveStatValue(AdditiveInitiative additiveStat) {
        return additiveStat.getInit();
    }

    @Override
    protected BuffedInitiative createBuffedStat(int buffedStatValue) {
        return new BuffedInitiative(buffedStatValue);
    }
}
