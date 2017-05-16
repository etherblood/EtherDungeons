package com.etherdungeons.engine.stats.buffed;

import com.etherdungeons.engine.stats.additive.AdditiveActionPoints;
import com.etherdungeons.engine.stats.additive.AdditiveStatsTarget;
import com.etherdungeons.engine.stats.base.BaseActionPoints;
import com.etherdungeons.entitysystem.EntityDataImpl;
import com.etherdungeons.entitysystem.EntityId;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Philipp
 */
public class BuffedActionPointsUpdateSystemTest {

    @Test
    public void multipleBuffs() {
        EntityDataImpl data = new EntityDataImpl();
        BuffedActionPointsUpdateSystem instance = new BuffedActionPointsUpdateSystem(data);
        
        EntityId base = data.createEntity();
        data.set(base, new BaseActionPoints(10));
        
        EntityId buff1 = data.createEntity();
        data.set(buff1, new AdditiveStatsTarget(base));
        data.set(buff1, new AdditiveActionPoints(5));
        
        EntityId buff2 = data.createEntity();
        data.set(buff2, new AdditiveStatsTarget(base));
        data.set(buff2, new AdditiveActionPoints(4));
        
        instance.run();
        assertEquals(19, data.get(base, BuffedActionPoints.class).getAp());
    }

}