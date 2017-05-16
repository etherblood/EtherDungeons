package com.etherdungeons.engine.gameflow.phases;

import com.etherdungeons.entitysystem.EntityComponent;

/**
 *
 * @author Philipp
 */
public class ActiveTurn implements EntityComponent {

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{}";
    }

}
