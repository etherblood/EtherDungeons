package com.etherdungeons.engine.stats.buffed;

import com.etherdungeons.engine.stats.additive.AdditiveMovePoints;
import com.etherdungeons.engine.stats.base.BaseMovePoints;
import com.etherdungeons.entitysystem.EntityData;

/**
 *
 * @author Philipp
 */
public class BuffedMovePointsUpdateSystem extends AbstractBuffedStatUpdateSystem<BaseMovePoints, AdditiveMovePoints, BuffedMovePoints>{

    public BuffedMovePointsUpdateSystem(EntityData data) {
        super(data);
    }

    @Override
    protected Class<BaseMovePoints> getBaseStatClass() {
        return BaseMovePoints.class;
    }

    @Override
    protected Class<AdditiveMovePoints> getAdditiveStatClass() {
        return AdditiveMovePoints.class;
    }

    @Override
    protected Class<BuffedMovePoints> getBuffedStatClass() {
        return BuffedMovePoints.class;
    }

    @Override
    protected int getBaseStatValue(BaseMovePoints baseStat) {
        return baseStat.getMp();
    }

    @Override
    protected int getAdditiveStatValue(AdditiveMovePoints additiveStat) {
        return additiveStat.getMp();
    }

    @Override
    protected BuffedMovePoints createBuffedStat(int buffedStatValue) {
        return new BuffedMovePoints(buffedStatValue);
    }
}
