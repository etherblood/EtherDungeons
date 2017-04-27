package com.etherdungeons.engine.gameflow;

import com.etherdungeons.entitysystem.EntityComponent;

/**
 *
 * @author Philipp
 */
public class Priority implements EntityComponent {
    private final int priority;

    public Priority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "priority=" + priority + '}';
    }
}
