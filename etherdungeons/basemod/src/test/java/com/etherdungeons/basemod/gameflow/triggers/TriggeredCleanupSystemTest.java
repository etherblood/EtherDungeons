package com.etherdungeons.basemod.gameflow.triggers;

import com.etherdungeons.basemod.data.gameflow.triggers.Triggered;
import com.etherdungeons.basemod.data.gameflow.triggers.TriggeredCleanupSystem;
import com.etherdungeons.basemod.data.core.Name;
import com.etherdungeons.entitysystem.EntityDataImpl;
import com.etherdungeons.entitysystem.EntityId;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Philipp
 */
public class TriggeredCleanupSystemTest {

    
    @Test
    public void cleanup() {
        EntityDataImpl data = new EntityDataImpl();
        TriggeredCleanupSystem instance = new TriggeredCleanupSystem(data);
        
        EntityId triggeredArgs = data.createEntity();
        data.set(triggeredArgs, new Triggered(data.createEntity()));
        data.set(triggeredArgs, new Name("test"));
        
        instance.run();
        assertTrue(data.components(triggeredArgs).isEmpty());
    }

}