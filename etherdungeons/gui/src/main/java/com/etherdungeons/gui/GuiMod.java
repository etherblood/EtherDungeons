package com.etherdungeons.gui;

import com.etherdungeons.basemod.BaseMod;
import com.etherdungeons.basemod.GameController;
import com.etherdungeons.basemod.actions.ActionManager;
import com.etherdungeons.context.definitions.BeanDefinitionFactory;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityDataReadonly;
import com.etherdungeons.gui.appstates.CamAppstate;
import com.etherdungeons.gui.appstates.GridAppstate;
import com.etherdungeons.gui.appstates.InputAppstate;
import com.etherdungeons.gui.appstates.LobbyAppstate;
import com.etherdungeons.gui.appstates.ModelAppstate;
import com.etherdungeons.gui.appstates.SkillbarAppstate;
import com.etherdungeons.gui.appstates.GameControlAppstate;
import com.etherdungeons.modding.Mod;
import com.etherdungeons.modding.SystemDefinition;
import com.jme3.app.StatsAppState;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class GuiMod implements Mod {

    @Override
    public Collection<SystemDefinition> getSystemDefinitions() {
        try {
            return getSystemDefinitionsThrows();
        } catch (NoSuchMethodException | SecurityException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Collection<SystemDefinition> getSystemDefinitionsThrows() throws NoSuchMethodException, SecurityException {
        return Arrays.asList(of(StatsAppState.class.getConstructor(), BaseMod.ORDER_DEFAULT),
                of(GameControlAppstate.class.getConstructor(EntityData.class, GameController.class, SkillbarAppstate.class), BaseMod.ORDER_DEFAULT),
                of(GridAppstate.class.getConstructor(EntityDataReadonly.class), BaseMod.ORDER_DEFAULT),
                of(SkillbarAppstate.class.getConstructor(EntityDataReadonly.class), 10000),//must be after UpdateGameAppstate
                of(CamAppstate.class.getConstructor(EntityDataReadonly.class), BaseMod.ORDER_DEFAULT),
                of(ModelAppstate.class.getConstructor(EntityDataReadonly.class), BaseMod.ORDER_TRIGGER),
                of(InputAppstate.class.getConstructor(GameControlAppstate.class), BaseMod.ORDER_DEFAULT),
                of(LobbyAppstate.class.getConstructor(), BaseMod.ORDER_DEFAULT)
        );
    }

    private static SystemDefinition of(Constructor<?> constructor, int priority) {
        return new SystemDefinition(BeanDefinitionFactory.of(constructor), priority);
    }

}
