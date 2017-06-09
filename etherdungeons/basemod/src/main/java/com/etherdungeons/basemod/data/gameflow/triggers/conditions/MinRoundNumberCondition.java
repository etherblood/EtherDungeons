package com.etherdungeons.basemod.data.gameflow.triggers.conditions;

import com.etherdungeons.entitysystem.EntityComponent;

/**
 *
 * @author Philipp
 */
public class MinRoundNumberCondition implements EntityComponent {

    private final int round;

    public MinRoundNumberCondition(int round) {
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
