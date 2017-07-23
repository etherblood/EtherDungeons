package com.etherdungeons.gui;

import com.etherdungeons.basemod.mods.BaseMod;
import com.etherdungeons.basemod.GameController;
import com.etherdungeons.basemod.mods.AbstractMod;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityDataReadonly;
import com.etherdungeons.gui.appstates.CamAppstate;
import com.etherdungeons.gui.appstates.GridAppstate;
import com.etherdungeons.gui.appstates.InputAppstate;
import com.etherdungeons.gui.appstates.LobbyAppstate;
import com.etherdungeons.gui.appstates.ModelAppstate;
import com.etherdungeons.gui.appstates.SkillbarAppstate;
import com.etherdungeons.gui.appstates.GameControlAppstate;
import com.jme3.app.StatsAppState;

/**
 *
 * @author Philipp
 */
public class GuiMod extends AbstractMod {

    public GuiMod() {
        try {
            init();
        } catch (NoSuchMethodException | SecurityException ex) {
            throw new RuntimeException();
        }
    }

    private void init() throws NoSuchMethodException, SecurityException {
        add(StatsAppState.class.getConstructor(), BaseMod.ORDER_DEFAULT);
        add(GameControlAppstate.class.getConstructor(EntityData.class, GameController.class, SkillbarAppstate.class), BaseMod.ORDER_DEFAULT);
        add(GridAppstate.class.getConstructor(EntityDataReadonly.class), BaseMod.ORDER_TARGETING);
        add(SkillbarAppstate.class.getConstructor(EntityDataReadonly.class), BaseMod.ORDER_DEFAULT);
        add(CamAppstate.class.getConstructor(EntityDataReadonly.class), BaseMod.ORDER_DEFAULT);
        add(ModelAppstate.class.getConstructor(EntityDataReadonly.class), BaseMod.ORDER_TRIGGER);
        add(InputAppstate.class.getConstructor(GameControlAppstate.class), BaseMod.ORDER_DEFAULT);
        add(LobbyAppstate.class.getConstructor(), BaseMod.ORDER_DEFAULT);
    }

}
