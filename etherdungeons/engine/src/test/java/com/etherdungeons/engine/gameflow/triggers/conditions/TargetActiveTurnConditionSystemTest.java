/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherdungeons.engine.gameflow.triggers.conditions;

import com.etherdungeons.engine.gameflow.effects.turnflow.phases.ActiveTurn;
import com.etherdungeons.engine.gameflow.triggers.TriggerRejected;
import com.etherdungeons.engine.gameflow.triggers.TriggerRequest;
import com.etherdungeons.engine.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.entitysystem.EntityDataImpl;
import com.etherdungeons.entitysystem.EntityId;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Philipp
 */
public class TargetActiveTurnConditionSystemTest {

    @Test
    public void turnIsActive() {
        EntityDataImpl data = new EntityDataImpl();
        
        EntityId target = data.createEntity();
        data.set(target, new ActiveTurn());
        
        EntityId trigger = data.createEntity();
        data.set(trigger, new TargetActiveTurnCondition());
        
        EntityId triggered = data.createEntity();
        data.set(triggered, new TriggerRequest(trigger));
        data.set(triggered, new TriggerArgsTargets(target));
        
        TargetsActiveTurnConditionSystem instance = new TargetsActiveTurnConditionSystem(data);
        instance.run();
        assertTrue(data.has(triggered, TriggerRequest.class));
        assertFalse(data.has(triggered, TriggerRejected.class));
    }

    @Test
    public void turnIsNotActive() {
        EntityDataImpl data = new EntityDataImpl();
        
        EntityId target = data.createEntity();
        
        EntityId trigger = data.createEntity();
        data.set(trigger, new TargetActiveTurnCondition());
        
        EntityId triggered = data.createEntity();
        data.set(triggered, new TriggerRequest(trigger));
        data.set(triggered, new TriggerArgsTargets(target));
        
        TargetsActiveTurnConditionSystem instance = new TargetsActiveTurnConditionSystem(data);
        instance.run();
        assertFalse(data.has(triggered, TriggerRequest.class));
        assertTrue(data.has(triggered, TriggerRejected.class));
    }

    @Test
    public void noTarget() {
        EntityDataImpl data = new EntityDataImpl();
        
        EntityId trigger = data.createEntity();
        data.set(trigger, new TargetActiveTurnCondition());
        
        EntityId triggered = data.createEntity();
        data.set(triggered, new TriggerRequest(trigger));
        
        TargetsActiveTurnConditionSystem instance = new TargetsActiveTurnConditionSystem(data);
        instance.run();
        assertFalse(data.has(triggered, TriggerRequest.class));
        assertTrue(data.has(triggered, TriggerRejected.class));
    }
    
}
