package com.etherdungeons.engine.game.health;

import com.simsilica.es.EntityComponent;

/**
 *
 * @author Philipp
 */
public class Health implements EntityComponent {
    private final int health;

    public Health(int health) {
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
