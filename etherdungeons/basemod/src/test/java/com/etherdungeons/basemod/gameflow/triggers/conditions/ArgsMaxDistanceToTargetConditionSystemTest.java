package com.etherdungeons.basemod.gameflow.triggers.conditions;

import com.etherdungeons.basemod.data.gameflow.triggers.conditions.ArgsMaxDistanceToTargetCondition;
import com.etherdungeons.basemod.data.gameflow.triggers.conditions.ArgsMaxDistanceToTargetConditionSystem;
import com.etherdungeons.basemod.data.gameflow.triggers.TriggerRejected;
import com.etherdungeons.basemod.data.gameflow.triggers.TriggerRequest;
import com.etherdungeons.basemod.data.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.basemod.data.position.Position;
import com.etherdungeons.entitysystem.EntityDataImpl;
import com.etherdungeons.entitysystem.EntityId;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Philipp
 */
public class ArgsMaxDistanceToTargetConditionSystemTest {

    public ArgsMaxDistanceToTargetConditionSystemTest() {
    }

    @Test
    public void distanceSmallerThanMax() {
        EntityDataImpl data = new EntityDataImpl();
        ArgsMaxDistanceToTargetConditionSystem instance = new ArgsMaxDistanceToTargetConditionSystem();
        
        EntityId target = data.createEntity();
        data.set(target, new Position(0, 0));
        
        EntityId trigger = data.createEntity();
        data.set(trigger, new ArgsMaxDistanceToTargetCondition(4));
        
        EntityId triggerArgs = data.createEntity();
        data.set(triggerArgs, new Position(2, 1));
        data.set(triggerArgs, new TriggerRequest(trigger));
        data.set(triggerArgs, new TriggerArgsTargets(target));
        
        instance.run(data);
        assertEquals(trigger, data.get(triggerArgs, TriggerRequest.class).getTrigger());
    }

    @Test
    public void distanceGreaterThanMax() {
        EntityDataImpl data = new EntityDataImpl();
        ArgsMaxDistanceToTargetConditionSystem instance = new ArgsMaxDistanceToTargetConditionSystem();
        
        EntityId target = data.createEntity();
        data.set(target, new Position(0, 0));
        
        EntityId trigger = data.createEntity();
        data.set(trigger, new ArgsMaxDistanceToTargetCondition(2));
        
        EntityId triggerArgs = data.createEntity();
        data.set(triggerArgs, new Position(2, 1));
        data.set(triggerArgs, new TriggerRequest(trigger));
        data.set(triggerArgs, new TriggerArgsTargets(target));
        
        instance.run(data);
        assertFalse(data.has(triggerArgs, TriggerRequest.class));
        assertEquals(trigger, data.get(triggerArgs, TriggerRejected.class).getTrigger());
    }

}