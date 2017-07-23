package com.etherdungeons.basemod.data.stats.active.reset;

import com.etherdungeons.basemod.data.stats.active.ActiveHealth;
import com.etherdungeons.basemod.data.stats.buffed.BuffedHealth;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;


public class ResetHealthSystem extends AbstractResetStatSystem<ResetHealthEffect> {

    @Override
    protected Class<ResetHealthEffect> getResetStatEffectClass() {
        return ResetHealthEffect.class;
    }

    @Override
    protected void reset(EntityData data, ResetHealthEffect reset, EntityId target) {
        BuffedHealth ap = data.get(target, BuffedHealth.class);
        if(ap != null) {
            data.set(target, new ActiveHealth(ap.getHealth()));
        } else {
            data.remove(target, ActiveHealth.class);
        }
    }

}
