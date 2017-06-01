package com.etherdungeons.engine.data.gameflow.effects.damage;

import com.etherdungeons.entitysystem.EntityComponent;

/**
 *
 * @author Philipp
 */
public class DamageEffect implements EntityComponent {
    private final int damage;

    public DamageEffect(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{damage=" + damage + '}';
    }
}
