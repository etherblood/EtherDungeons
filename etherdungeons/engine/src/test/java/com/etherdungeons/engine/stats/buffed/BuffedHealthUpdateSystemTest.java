package com.etherdungeons.engine.stats.buffed;

import com.etherdungeons.engine.data.stats.buffed.BuffedHealth;
import com.etherdungeons.engine.data.stats.buffed.BuffedHealthUpdateSystem;
import com.etherdungeons.engine.data.stats.additive.AdditiveHealth;
import com.etherdungeons.engine.data.stats.additive.AdditiveStatsTarget;
import com.etherdungeons.engine.data.stats.base.BaseHealth;
import com.etherdungeons.entitysystem.EntityDataImpl;
import com.etherdungeons.entitysystem.EntityId;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Philipp
 */
public class BuffedHealthUpdateSystemTest {

    @Test
    public void multipleBuffs() {
        EntityDataImpl data = new EntityDataImpl();
        BuffedHealthUpdateSystem instance = new BuffedHealthUpdateSystem(data);
        
        EntityId base = data.createEntity();
        data.set(base, new BaseHealth(10));
        
        EntityId buff1 = data.createEntity();
        data.set(buff1, new AdditiveStatsTarget(base));
        data.set(buff1, new AdditiveHealth(5));
        
        EntityId buff2 = data.createEntity();
        data.set(buff2, new AdditiveStatsTarget(base));
        data.set(buff2, new AdditiveHealth(4));
        
        instance.run();
        assertEquals(19, data.get(base, BuffedHealth.class).getHealth());
    }

}