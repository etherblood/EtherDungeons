package com.etherdungeons.engine.data.stats.buffed;

import com.etherdungeons.engine.data.stats.additive.AdditiveActionPoints;
import com.etherdungeons.engine.data.stats.base.BaseActionPoints;
import com.etherdungeons.entitysystem.EntityData;

/**
 *
 * @author Philipp
 */
public class BuffedActionPointsUpdateSystem extends AbstractBuffedStatUpdateSystem<BaseActionPoints, AdditiveActionPoints, BuffedActionPoints>{

    public BuffedActionPointsUpdateSystem(EntityData data) {
        super(data);
    }

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
