package com.etherdungeons.basemod.data.stats.active.reset;

import com.etherdungeons.basemod.data.stats.active.ActiveMovePoints;
import com.etherdungeons.basemod.data.stats.buffed.BuffedMovePoints;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;


public class ResetMpSystem extends AbstractResetStatSystem<ResetMpEffect> {

    public ResetMpSystem(EntityData data) {
        super(data);
    }

    @Override
    protected Class<ResetMpEffect> getResetStatEffectClass() {
        return ResetMpEffect.class;
    }

    @Override
    protected void reset(ResetMpEffect reset, EntityId target) {
        BuffedMovePoints ap = data.get(target, BuffedMovePoints.class);
        if(ap != null) {
            data.set(target, new ActiveMovePoints(ap.getMp()));
        } else {
            data.remove(target, ActiveMovePoints.class);
        }
    }

}
