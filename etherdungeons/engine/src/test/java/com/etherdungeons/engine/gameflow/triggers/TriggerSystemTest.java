package com.etherdungeons.engine.gameflow.triggers;

import com.etherdungeons.engine.core.Name;
import com.etherdungeons.entitysystem.EntityDataImpl;
import com.etherdungeons.entitysystem.EntityId;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Philipp
 */
public class TriggerSystemTest {

    @Test
    public void trigger() {
        EntityDataImpl data = new EntityDataImpl();
        TriggerSystem instance = new TriggerSystem(data);
        
        EntityId trigger = data.createEntity();
        
        EntityId triggeredArgs = data.createEntity();
        data.set(triggeredArgs, new TriggerRequest(trigger));
        
        instance.run();
        assertEquals(trigger, data.get(triggeredArgs, Triggered.class).getTrigger());
        assertFalse(data.has(triggeredArgs, TriggerRequest.class));
    }
    
    @Test
    public void cleanup() {
        EntityDataImpl data = new EntityDataImpl();
        TriggerSystem instance = new TriggerSystem(data);
        
        EntityId triggeredArgs = data.createEntity();
        data.set(triggeredArgs, new Triggered(data.createEntity()));
        data.set(triggeredArgs, new Name("test"));
        
        instance.run();
        assertTrue(data.components(triggeredArgs).isEmpty());
    }

}