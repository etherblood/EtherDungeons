package com.etherdungeons.gui;

import com.etherdungeons.gui.appstates.GridAppstate;
import com.etherdungeons.basemod.data.gameflow.MapSize;
import com.etherdungeons.basemod.data.gameflow.triggers.TriggerRequest;
import com.etherdungeons.context.Context;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityDataReadonly;
import com.etherdungeons.gui.appstates.CamAppstate;
import com.etherdungeons.gui.appstates.InputAppstate;
import com.etherdungeons.gui.appstates.ModelAppstate;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.math.Vector3f;
import com.jme3.system.AppSettings;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Context gameContext = new GameSetup().setup();
        Main app = new Main(gameContext);
        AppSettings settings = new AppSettings(true);
        settings.setResolution(1280, 720);
        settings.setVSync(true);
        app.setSettings(settings);

        app.setShowSettings(false);
        app.start(); // start the game
    }

    public Main(Context gameContext) {
        super(initAppStates(gameContext));
    }
    
    private static AppState[] initAppStates(Context gameContext) {
        EntityDataReadonly data = gameContext.getBean(EntityDataReadonly.class);
        return new AppState[]{
            new GridAppstate(data),
            new InputAppstate(gameContext),
            new ModelAppstate(data),
            new CamAppstate(data)
        };
    }

    @Override
    public void simpleInitApp() {
    }

}
