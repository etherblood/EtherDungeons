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
public class TriggerRejectedSystemTest {

    @Test
    public void cleanup() {
        EntityDataImpl data = new EntityDataImpl();
        TriggerRejectedSystem instance = new TriggerRejectedSystem(data);
        
        EntityId triggeredArgs = data.createEntity();
        data.set(triggeredArgs, new TriggerRejected(data.createEntity(), "test"));
        data.set(triggeredArgs, new Name("test"));
        
        instance.run();
        assertTrue(data.components(triggeredArgs).isEmpty());
    }

}