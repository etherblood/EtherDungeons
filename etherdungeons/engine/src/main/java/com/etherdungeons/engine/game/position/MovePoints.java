package com.etherdungeons.engine.game.position;

import com.simsilica.es.EntityComponent;

/**
 *
 * @author Philipp
 */
public class MovePoints implements EntityComponent {
    private final int mp;

    public MovePoints(int mp) {
        this.mp = mp;
    }

    public int getMp() {
        return mp;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "mp=" + mp + '}';
    }
}
