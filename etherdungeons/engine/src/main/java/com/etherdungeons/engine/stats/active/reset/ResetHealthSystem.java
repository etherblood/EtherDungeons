package com.etherdungeons.engine.stats.active.reset;

import com.etherdungeons.engine.stats.active.ActiveHealth;
import com.etherdungeons.engine.stats.buffed.BuffedHealth;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;


public class ResetHealthSystem extends AbstractResetStatSystem<ResetHealthEffect> {

    public ResetHealthSystem(EntityData data) {
        super(data);
    }

    @Override
    protected Class<ResetHealthEffect> getResetStatEffectClass() {
        return ResetHealthEffect.class;
    }

    @Override
    protected void reset(ResetHealthEffect reset, EntityId target) {
        BuffedHealth ap = data.get(target, BuffedHealth.class);
        if(ap != null) {
            data.set(target, new ActiveHealth(ap.getHealth()));
        } else {
            data.remove(target, ActiveHealth.class);
        }
    }

}
