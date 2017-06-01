package com.etherdungeons.engine.data.stats.additive;

import com.etherdungeons.entitysystem.EntityComponent;

/**
 *
 * @author Philipp
 */
public class AdditiveInitiative implements EntityComponent {
    private final int init;

    public AdditiveInitiative(int init) {
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
