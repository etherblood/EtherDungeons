package com.etherdungeons.basemod.data.gameflow.triggers.triggerargs;

import com.etherdungeons.entitysystem.EntityComponent;
import com.etherdungeons.entitysystem.EntityId;
import java.util.Arrays;

/**
 *
 * @author Philipp
 */
public class TriggerArgsTargets implements EntityComponent {

    private final EntityId[] targets;

    public TriggerArgsTargets(EntityId... target) {
        this.targets = target;
    }

    public EntityId getTarget() {
        if(targets.length != 1) {
            throw new IllegalStateException(toString());
        }
        return targets[0];
    }

    public EntityId[] getTargets() {
        return targets;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{targets=" + Arrays.toString(targets) + '}';
    }
}
