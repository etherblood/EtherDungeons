package com.etherdungeons.basemod.data.gameflow.effects.spiritclaw;

import com.etherdungeons.basemod.GameSystem;
import com.etherdungeons.basemod.data.gameflow.effects.damage.DamageEffect;
import com.etherdungeons.basemod.data.gameflow.triggers.TriggerRequest;
import com.etherdungeons.basemod.data.gameflow.triggers.Triggered;
import com.etherdungeons.basemod.data.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class SpiritClawSystem implements GameSystem {

    private static final int SPIRIT_CLAW_DAMAGE = 3;

    @Override
    public void run(EntityData data) {
        for (EntityId triggerArgs : data.entities(Triggered.class, TriggerArgsTargets.class)) {
            EntityId effect = data.get(triggerArgs, Triggered.class).getTrigger();
            if (data.has(effect, SpiritClawEffect.class)) {
                EntityId damage = data.createEntity();
                data.set(damage, new DamageEffect(SPIRIT_CLAW_DAMAGE));
                data.set(damage, new TriggerRequest(damage));
                data.set(damage, data.get(triggerArgs, TriggerArgsTargets.class));
            }
        }
    }

}
