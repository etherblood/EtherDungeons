package com.etherdungeons.basemod.gameflow.triggers;

import com.etherdungeons.basemod.data.gameflow.triggers.Triggered;
import com.etherdungeons.basemod.data.gameflow.triggers.TriggeredCleanupSystem;
import com.etherdungeons.basemod.data.core.Name;
import com.etherdungeons.entitysystem.EntityDataImpl;
import com.etherdungeons.entitysystem.EntityId;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.helpers.NOPLogger;

/**
 *
 * @author Philipp
 */
public class TriggeredCleanupSystemTest {

    
    @Test
    public void cleanup() {
        EntityDataImpl data = new EntityDataImpl();
        TriggeredCleanupSystem instance = new TriggeredCleanupSystem();
        
        EntityId triggeredArgs = data.createEntity();
        data.set(triggeredArgs, new Triggered(data.createEntity()));
        data.set(triggeredArgs, new Name("test"));
        
        instance.run(data, NOPLogger.NOP_LOGGER);
        assertTrue(data.components(triggeredArgs).isEmpty());
    }

}