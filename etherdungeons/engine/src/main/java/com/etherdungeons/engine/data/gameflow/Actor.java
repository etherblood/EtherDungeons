package com.etherdungeons.engine.data.gameflow;

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
