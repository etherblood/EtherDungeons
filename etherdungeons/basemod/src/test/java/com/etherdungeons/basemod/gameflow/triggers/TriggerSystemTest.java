package com.etherdungeons.basemod.gameflow.triggers;

import com.etherdungeons.basemod.data.gameflow.triggers.TriggerRequest;
import com.etherdungeons.basemod.data.gameflow.triggers.Triggered;
import com.etherdungeons.basemod.data.gameflow.triggers.TriggerSystem;
import com.etherdungeons.basemod.data.core.Name;
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
        TriggerSystem instance = new TriggerSystem();
        
        EntityId trigger = data.createEntity();
        
        EntityId triggeredArgs = data.createEntity();
        data.set(triggeredArgs, new TriggerRequest(trigger));
        
        instance.run(data);
        assertEquals(trigger, data.get(triggeredArgs, Triggered.class).getTrigger());
        assertFalse(data.has(triggeredArgs, TriggerRequest.class));
    }

}