package com.etherdungeons.gui;

import com.etherdungeons.basemod.BaseMod;
import com.etherdungeons.context.Context;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import com.etherdungeons.modding.ModCombiner;
import com.etherdungeons.templates.TemplateService;
import java.io.IOException;

/**
 *
 * @author Philipp
 */
public class GameSetup {
    public Context setup() {
        return initGame();
    }

    private static Context initGame() throws RuntimeException {
        try {
            return initGameThrows();
        } catch (ReflectiveOperationException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static Context initGameThrows() throws ReflectiveOperationException, IOException {
        Context gameContext = new ModCombiner(new BaseMod(), new GuiMod()).build();
        TemplateService templates = gameContext.getBean(TemplateService.class);
        EntityData data = gameContext.getBean(EntityData.class);

        EntityId amara = data.createEntity();
        templates.get("amara").applyTemplate(data, amara);

        EntityId gobball_1 = data.createEntity();
        templates.get("gobball").applyTemplate(data, gobball_1);

        EntityId gobball_2 = data.createEntity();
        templates.get("gobball").applyTemplate(data, gobball_2);

        EntityId gobball_3 = data.createEntity();
        templates.get("gobball").applyTemplate(data, gobball_3);

        templates.get("test_map").applyTemplate(data, amara, gobball_1, gobball_2, gobball_3);

        return gameContext;
    }
}
