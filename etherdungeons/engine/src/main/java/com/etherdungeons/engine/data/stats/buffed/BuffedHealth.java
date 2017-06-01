package com.etherdungeons.engine.data.stats.buffed;

import com.etherdungeons.entitysystem.EntityComponent;

/**
 *
 * @author Philipp
 */
public class BuffedHealth implements EntityComponent {
    private final int health;

    public BuffedHealth(int health) {
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
