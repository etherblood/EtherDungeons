package com.etherdungeons.basemod;

import com.etherdungeons.entitysystem.EntityData;
import org.slf4j.Logger;

/**
 *
 * @author Philipp
 */
public interface GameSystem {
    void run(EntityData data, Logger log);
}
