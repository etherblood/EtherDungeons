package com.etherdungeons.engine.stats.buffed;

import com.etherdungeons.engine.core.Target;
import com.etherdungeons.engine.stats.additive.AdditiveInitiative;
import com.etherdungeons.engine.stats.base.BaseInitiative;
import com.etherdungeons.entitysystem.EntityDataImpl;
import com.etherdungeons.entitysystem.EntityId;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Philipp
 */
public class BuffedInitiativeUpdateSystemTest {

    @Test
    public void multipleBuffs() {
        EntityDataImpl data = new EntityDataImpl();
        BuffedInitiativeUpdateSystem instance = new BuffedInitiativeUpdateSystem(data);
        
        EntityId base = data.createEntity();
        data.set(base, new BaseInitiative(10));
        
        EntityId buff1 = data.createEntity();
        data.set(buff1, new Target(base));
        data.set(buff1, new AdditiveInitiative(5));
        
        EntityId buff2 = data.createEntity();
        data.set(buff2, new Target(base));
        data.set(buff2, new AdditiveInitiative(4));
        
        instance.run();
        assertEquals(19, data.get(base, BuffedInitiative.class).getInit());
    }

}