package com.etherdungeons.engine.data.gameflow.effects.heal;

import com.etherdungeons.entitysystem.EntityComponent;

/**
 *
 * @author Philipp
 */
public class HealEffect implements EntityComponent {
    private final int heal;

    public HealEffect(int heal) {
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
