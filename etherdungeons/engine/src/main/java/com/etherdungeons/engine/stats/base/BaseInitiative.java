package com.etherdungeons.engine.stats.base;

import com.etherdungeons.entitysystem.EntityComponent;

/**
 *
 * @author Philipp
 */
public class BaseInitiative implements EntityComponent {
    private final int init;

    public BaseInitiative(int init) {
        this.init = init;
    }

    public int getInit() {
        return init;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "init=" + init + '}';
    }
}
