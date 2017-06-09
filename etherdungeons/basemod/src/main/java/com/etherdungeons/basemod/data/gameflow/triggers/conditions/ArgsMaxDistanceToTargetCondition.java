package com.etherdungeons.basemod.data.gameflow.triggers.conditions;

import com.etherdungeons.entitysystem.EntityComponent;

/**
 *
 * @author Philipp
 */
public class ArgsMaxDistanceToTargetCondition implements EntityComponent {

    private final int distance;

    public ArgsMaxDistanceToTargetCondition(int distance) {
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{distance=" + distance + '}';
    }

}
