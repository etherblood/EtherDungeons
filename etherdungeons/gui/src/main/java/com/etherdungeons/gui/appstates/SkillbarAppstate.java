package com.etherdungeons.gui.appstates;

import com.etherdungeons.basemod.data.core.Name;
import com.etherdungeons.basemod.data.gameflow.effects.Ability;
import com.etherdungeons.basemod.data.gameflow.effects.move.MoveEffect;
import com.etherdungeons.basemod.data.gameflow.effects.turnflow.phases.ActiveTurn;
import com.etherdungeons.entitysystem.EntityDataReadonly;
import com.etherdungeons.entitysystem.EntityId;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.simsilica.lemur.Axis;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.component.SpringGridLayout;

/**
 *
 * @author Philipp
 */
public class SkillbarAppstate extends BaseAppState {

    private final EntityDataReadonly data;
    private Container container;

    public SkillbarAppstate(EntityDataReadonly data) {
        this.data = data;
        setEnabled(false);
    }

    public void refreshSkills() {
        container.clearChildren();
        EntityId actor = data.entity(ActiveTurn.class);
        data.streamEntities(Ability.class).filter(e -> data.get(e, Ability.class).getOwner().equals(actor)).forEach(entity -> {
            Button button = new Button(data.get(entity, Name.class).getName());
            button.addClickCommands(new Command<Button>() {
                @Override
                public void execute(Button source) {
                    getState(GameControlAppstate.class).selectAbility(entity);
                }
            });
            if(data.has(entity, MoveEffect.class)) {
                getState(GameControlAppstate.class).selectAbility(entity);
            }
            container.addChild(button);
        });
    }

    @Override
    protected void initialize(Application app) {
        container = new Container(new SpringGridLayout(Axis.X, Axis.Y));
        container.setLocalTranslation(300, 300, 0);
    }

    @Override
    protected void cleanup(Application app) {
        container = null;
    }

    @Override
    protected void onEnable() {
        refreshSkills();
        ((SimpleApplication) getApplication()).getGuiNode().attachChild(container);
    }

    @Override
    protected void onDisable() {
        ((SimpleApplication) getApplication()).getGuiNode().detachChild(container);
    }

}
