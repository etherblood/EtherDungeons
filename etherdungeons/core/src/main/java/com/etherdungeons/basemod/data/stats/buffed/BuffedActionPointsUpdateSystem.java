package com.etherdungeons.basemod.data.stats.buffed;

import com.etherdungeons.basemod.data.stats.additive.AdditiveActionPoints;
import com.etherdungeons.basemod.data.stats.base.BaseActionPoints;

/**
 *
 * @author Philipp
 */
public class BuffedActionPointsUpdateSystem extends AbstractBuffedStatUpdateSystem<BaseActionPoints, AdditiveActionPoints, BuffedActionPoints>{

    @Override
    protected Class<BaseActionPoints> getBaseStatClass() {
        return BaseActionPoints.class;
    }

    @Override
    protected Class<AdditiveActionPoints> getAdditiveStatClass() {
        return AdditiveActionPoints.class;
    }

    @Override
    protected Class<BuffedActionPoints> getBuffedStatClass() {
        return BuffedActionPoints.class;
    }

    @Override
    protected int getBaseStatValue(BaseActionPoints baseStat) {
        return baseStat.getAp();
    }

    @Override
    protected int getAdditiveStatValue(AdditiveActionPoints additiveStat) {
        return additiveStat.getAp();
    }

    @Override
    protected BuffedActionPoints createBuffedStat(int buffedStatValue) {
        return new BuffedActionPoints(buffedStatValue);
    }
}
