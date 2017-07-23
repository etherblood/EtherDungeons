package com.etherdungeons.basemod.data.gameflow.effects.death;

import com.etherdungeons.basemod.AbstractContextIntegrationTest;
import com.etherdungeons.basemod.data.stats.active.ActiveHealth;
import com.etherdungeons.entitysystem.EntityId;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Philipp
 */
public class DeathSystemTest extends AbstractContextIntegrationTest {

    @Test
    public void run() {
        EntityId actor = data.createEntity();
        data.set(actor, new ActiveHealth(-1));
        controller.updateGame();
        assertTrue(data.components(actor).isEmpty());
    }

}
