package com.etherdungeons.engine.data.stats.buffs;

import com.etherdungeons.entitysystem.EntityComponent;
import com.etherdungeons.entitysystem.EntityId;
import java.util.Arrays;

/**
 *
 * @author Philipp
 */
public class BuffTargets implements EntityComponent {

    private final EntityId[] targets;

    public BuffTargets(EntityId... target) {
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
