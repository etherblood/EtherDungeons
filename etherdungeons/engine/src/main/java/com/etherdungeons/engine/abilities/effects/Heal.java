package com.etherdungeons.engine.abilities.effects;

import com.etherdungeons.entitysystem.EntityComponent;

/**
 *
 * @author Philipp
 */
public class Heal implements EntityComponent {
    private final int heal;

    public Heal(int heal) {
        this.heal = heal;
    }

    public int getHeal() {
        return heal;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{heal=" + heal + '}';
    }
}
