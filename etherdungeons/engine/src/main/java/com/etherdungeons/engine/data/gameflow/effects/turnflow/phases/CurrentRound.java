package com.etherdungeons.engine.data.gameflow.effects.turnflow.phases;

import com.etherdungeons.entitysystem.EntityComponent;

/**
 *
 * @author Philipp
 */
public class CurrentRound implements EntityComponent {

    private final int round;

    public CurrentRound(int round) {
        this.round = round;
    }

    public int getRound() {
        return round;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{round=" + round + '}';
    }
}
