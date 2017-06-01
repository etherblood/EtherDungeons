package com.etherdungeons.engine.data.gameflow.effects.spiritclaw;

import com.etherdungeons.engine.data.gameflow.effects.damage.DamageEffect;
import com.etherdungeons.engine.data.gameflow.triggers.TriggerRequest;
import com.etherdungeons.engine.data.gameflow.triggers.Triggered;
import com.etherdungeons.engine.data.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class SpiritClawSystem implements Runnable {

    private static final int SPIRIT_CLAW_DAMAGE = 3;
    private final EntityData data;

    public SpiritClawSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
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
