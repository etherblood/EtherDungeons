package com.etherdungeons.basemod.data.gameflow.effects.cleanup;

import com.etherdungeons.basemod.data.gameflow.triggers.Triggered;
import com.etherdungeons.basemod.data.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Philipp
 */
public class CleanupSystem implements Runnable {

    private final EntityData data;
    private final static Logger log = LoggerFactory.getLogger(CleanupSystem.class);

    public CleanupSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
        for (EntityId triggerArgs : data.entities(Triggered.class, TriggerArgsTargets.class)) {
            EntityId trigger = data.get(triggerArgs, Triggered.class).getTrigger();
            if (data.has(trigger, CleanupEffect.class)) {
                for (EntityId target : data.get(triggerArgs, TriggerArgsTargets.class).getTargets()) {
                    log.info("cleaning entity {} with components {}", target, data.components(target));
                    data.clearEntity(target);
                }
            }
        }
    }

}
