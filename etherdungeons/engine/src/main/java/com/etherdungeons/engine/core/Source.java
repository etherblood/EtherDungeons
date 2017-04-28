package com.etherdungeons.engine.core;

import com.etherdungeons.entitysystem.EntityComponent;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class Source implements EntityComponent {
    private final EntityId source;

    public Source(EntityId source) {
        this.source = source;
    }

    public EntityId getSource() {
        return source;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{source=" + source + '}';
    }
}
