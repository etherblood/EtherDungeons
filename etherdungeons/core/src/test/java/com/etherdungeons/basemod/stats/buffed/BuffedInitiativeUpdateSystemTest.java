package com.etherdungeons.basemod.stats.buffed;

import com.etherdungeons.basemod.data.stats.buffed.BuffedInitiative;
import com.etherdungeons.basemod.data.stats.buffed.BuffedInitiativeUpdateSystem;
import com.etherdungeons.basemod.data.stats.additive.AdditiveInitiative;
import com.etherdungeons.basemod.data.stats.additive.AdditiveStatsTarget;
import com.etherdungeons.basemod.data.stats.base.BaseInitiative;
import com.etherdungeons.entitysystem.EntityDataImpl;
import com.etherdungeons.entitysystem.EntityId;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.helpers.NOPLogger;

/**
 *
 * @author Philipp
 */
public class BuffedInitiativeUpdateSystemTest {

    @Test
    public void multipleBuffs() {
        EntityDataImpl data = new EntityDataImpl();
        BuffedInitiativeUpdateSystem instance = new BuffedInitiativeUpdateSystem();
        
        EntityId base = data.createEntity();
        data.set(base, new BaseInitiative(10));
        
        EntityId buff1 = data.createEntity();
        data.set(buff1, new AdditiveStatsTarget(base));
        data.set(buff1, new AdditiveInitiative(5));
        
        EntityId buff2 = data.createEntity();
        data.set(buff2, new AdditiveStatsTarget(base));
        data.set(buff2, new AdditiveInitiative(4));
        
        instance.run(data, NOPLogger.NOP_LOGGER);
        assertEquals(19, data.get(base, BuffedInitiative.class).getInit());
    }

}