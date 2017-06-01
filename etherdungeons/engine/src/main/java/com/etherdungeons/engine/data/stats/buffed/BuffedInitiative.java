package com.etherdungeons.engine.data.stats.buffed;

import com.etherdungeons.entitysystem.EntityComponent;

/**
 *
 * @author Philipp
 */
public class BuffedInitiative implements EntityComponent {
    private final int init;

    public BuffedInitiative(int init) {
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
