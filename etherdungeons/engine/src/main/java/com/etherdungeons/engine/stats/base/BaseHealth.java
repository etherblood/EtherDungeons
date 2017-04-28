package com.etherdungeons.engine.stats.base;

import com.etherdungeons.entitysystem.EntityComponent;

/**
 *
 * @author Philipp
 */
public class BaseHealth implements EntityComponent {
    private final int health;

    public BaseHealth(int health) {
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
