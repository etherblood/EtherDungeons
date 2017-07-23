package com.etherdungeons.basemod;

import com.etherdungeons.basemod.mods.BaseMod;
import com.etherdungeons.basemod.mods.SimpleLoggerMod;
import com.etherdungeons.context.Context;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.modding.ModCombiner;

/**
 *
 * @author Philipp
 */
public abstract class AbstractContextIntegrationTest {

    protected final Context context = new ModCombiner(new BaseMod(), new SimpleLoggerMod()).build();
    protected final EntityData data = context.getBean(EntityData.class);
    protected final GameController controller = context.getBean(GameController.class);

}
