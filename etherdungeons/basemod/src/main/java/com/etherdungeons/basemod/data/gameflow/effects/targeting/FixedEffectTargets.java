package com.etherdungeons.basemod.data.gameflow.effects.targeting;

import com.etherdungeons.entitysystem.EntityComponent;
import com.etherdungeons.entitysystem.EntityId;
import java.util.Arrays;

/**
 *
 * @author Philipp
 */
public class FixedEffectTargets implements EntityComponent {

    private final EntityId[] targets;

    public FixedEffectTargets(EntityId... target) {
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
