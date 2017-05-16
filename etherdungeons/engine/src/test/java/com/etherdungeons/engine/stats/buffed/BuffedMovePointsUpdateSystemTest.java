package com.etherdungeons.engine.stats.buffed;

import com.etherdungeons.engine.stats.additive.AdditiveMovePoints;
import com.etherdungeons.engine.stats.additive.AdditiveStatsTarget;
import com.etherdungeons.engine.stats.base.BaseMovePoints;
import com.etherdungeons.entitysystem.EntityDataImpl;
import com.etherdungeons.entitysystem.EntityId;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Philipp
 */
public class BuffedMovePointsUpdateSystemTest {

    @Test
    public void multipleBuffs() {
        EntityDataImpl data = new EntityDataImpl();
        BuffedMovePointsUpdateSystem instance = new BuffedMovePointsUpdateSystem(data);
        
        EntityId base = data.createEntity();
        data.set(base, new BaseMovePoints(10));
        
        EntityId buff1 = data.createEntity();
        data.set(buff1, new AdditiveStatsTarget(base));
        data.set(buff1, new AdditiveMovePoints(5));
        
        EntityId buff2 = data.createEntity();
        data.set(buff2, new AdditiveStatsTarget(base));
        data.set(buff2, new AdditiveMovePoints(4));
        
        instance.run();
        assertEquals(19, data.get(base, BuffedMovePoints.class).getMp());
    }

}