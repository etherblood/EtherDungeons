package com.etherdungeons.engine.stats;

import com.etherdungeons.entitysystem.EntityComponent;

/**
 *
 * @author Philipp
 */
public class ActionPoints implements EntityComponent {
    private final int ap;

    public ActionPoints(int ap) {
        this.ap = ap;
    }

    public int getAp() {
        return ap;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "ap=" + ap + '}';
    }
}
