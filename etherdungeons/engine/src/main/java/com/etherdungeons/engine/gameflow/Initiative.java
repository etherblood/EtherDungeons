package com.etherdungeons.engine.gameflow;

import com.etherdungeons.entitysystem.EntityComponent;

/**
 *
 * @author Philipp
 */
public class Initiative implements EntityComponent {
    private final int init;

    public Initiative(int init) {
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
