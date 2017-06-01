package com.etherdungeons.engine.data.stats.active;

import com.etherdungeons.entitysystem.EntityComponent;

/**
 *
 * @author Philipp
 */
public class ActiveHealth implements EntityComponent {
    private final int health;

    public ActiveHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "health=" + health + '}';
    }
}
