package com.etherdungeons.engine.stats.reset;

import com.etherdungeons.engine.stats.active.ActiveActionPoints;
import com.etherdungeons.engine.stats.buffed.BuffedActionPoints;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;


public class ResetApSystem extends AbstractResetStatSystem<ResetApEffect> {

    public ResetApSystem(EntityData data) {
        super(data);
    }

    @Override
    protected Class<ResetApEffect> getResetStatEffectClass() {
        return ResetApEffect.class;
    }

    @Override
    protected void reset(ResetApEffect reset, EntityId target) {
        BuffedActionPoints ap = data.get(target, BuffedActionPoints.class);
        if(ap != null) {
            data.set(target, new ActiveActionPoints(ap.getAp()));
        } else {
            data.remove(target, ActiveActionPoints.class);
        }
    }

}
