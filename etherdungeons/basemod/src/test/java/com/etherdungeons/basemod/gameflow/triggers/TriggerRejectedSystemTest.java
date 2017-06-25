package com.etherdungeons.basemod.gameflow.triggers;

import com.etherdungeons.basemod.data.gameflow.triggers.TriggerRejectedSystem;
import com.etherdungeons.basemod.data.gameflow.triggers.TriggerRejected;
import com.etherdungeons.basemod.data.core.Name;
import com.etherdungeons.entitysystem.EntityDataImpl;
import com.etherdungeons.entitysystem.EntityId;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Philipp
 */
public class TriggerRejectedSystemTest {

    @Test
    public void cleanup() {
        EntityDataImpl data = new EntityDataImpl();
        TriggerRejectedSystem instance = new TriggerRejectedSystem();
        
        EntityId triggeredArgs = data.createEntity();
        data.set(triggeredArgs, new TriggerRejected(data.createEntity(), "test"));
        data.set(triggeredArgs, new Name("test"));
        
        instance.run(data);
        assertTrue(data.components(triggeredArgs).isEmpty());
    }

}