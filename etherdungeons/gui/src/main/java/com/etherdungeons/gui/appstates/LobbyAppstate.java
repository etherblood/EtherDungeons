package com.etherdungeons.gui.appstates;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.TextField;

/**
 *
 * @author Philipp
 */
public class LobbyAppstate extends BaseAppState {
    private Container container;

    @Override
    protected void initialize(Application app) {
        container = new Container();
        TextField text = new TextField("amara");
        text.setPreferredWidth(200);
        container.addChild(text);
//        ListBox<String> list = new ListBox<>(new VersionedList<>(Arrays.asList("amara", "gobbal")));
//        container.addChild(list);

        Button startGameButton = new Button("start");
        startGameButton.addClickCommands(new Command<Button>() {
            @Override
            public void execute(Button source) {
                getState(GameControlAppstate.class).startGame();
            }
        });
        container.addChild(startGameButton);
        container.setLocalTranslation(400, 400, 0);
    }

    public LobbyAppstate() {
    }

    @Override
    protected void cleanup(Application app) {
        container = null;
    }

    @Override
    protected void onEnable() {
        ((SimpleApplication)getApplication()).getGuiNode().attachChild(container);
    }

    @Override
    protected void onDisable() {
        ((SimpleApplication)getApplication()).getGuiNode().detachChild(container);
    }
    
}
