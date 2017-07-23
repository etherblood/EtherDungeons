package com.etherdungeons.basemod.stats.buffed;

import com.etherdungeons.basemod.data.stats.buffed.BuffedMovePoints;
import com.etherdungeons.basemod.data.stats.buffed.BuffedMovePointsUpdateSystem;
import com.etherdungeons.basemod.data.stats.additive.AdditiveMovePoints;
import com.etherdungeons.basemod.data.stats.additive.AdditiveStatsTarget;
import com.etherdungeons.basemod.data.stats.base.BaseMovePoints;
import com.etherdungeons.entitysystem.EntityDataImpl;
import com.etherdungeons.entitysystem.EntityId;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.helpers.NOPLogger;

/**
 *
 * @author Philipp
 */
public class BuffedMovePointsUpdateSystemTest {

    @Test
    public void multipleBuffs() {
        EntityDataImpl data = new EntityDataImpl();
        BuffedMovePointsUpdateSystem instance = new BuffedMovePointsUpdateSystem();
        
        EntityId base = data.createEntity();
        data.set(base, new BaseMovePoints(10));
        
        EntityId buff1 = data.createEntity();
        data.set(buff1, new AdditiveStatsTarget(base));
        data.set(buff1, new AdditiveMovePoints(5));
        
        EntityId buff2 = data.createEntity();
        data.set(buff2, new AdditiveStatsTarget(base));
        data.set(buff2, new AdditiveMovePoints(4));
        
        instance.run(data, NOPLogger.NOP_LOGGER);
        assertEquals(19, data.get(base, BuffedMovePoints.class).getMp());
    }

}