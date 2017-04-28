package com.etherdungeons.engine.gameflow;

import com.etherdungeons.entitysystem.EntityComponent;

/**
 *
 * @author Philipp
 */
public class Actor implements EntityComponent {

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{}";
    }

}
