package com.etherdungeons.basemod.data.stats.buffed;

import com.etherdungeons.entitysystem.EntityComponent;

/**
 *
 * @author Philipp
 */
public class BuffedActionPoints implements EntityComponent {
    private final int ap;

    public BuffedActionPoints(int ap) {
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
