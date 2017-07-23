package com.etherdungeons.gui;

import com.etherdungeons.context.Context;
import com.etherdungeons.gui.appstates.LobbyAppstate;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.system.AppSettings;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.style.BaseStyles;

/**
 *
 * @author Philipp
 */
public class Main extends SimpleApplication {

    private final Context gameContext;
    
    public static void main(String[] args) {
        Context gameContext = new GameSetup().setup();
        System.out.println(gameContext);
        Main app = new Main(gameContext);
        
        
        AppSettings settings = new AppSettings(true);
        settings.setResolution(1280, 720);
        settings.setVSync(true);
        app.setSettings(settings);

        app.setShowSettings(false);
        app.start(); // start the game
    }

    public Main(Context gameContext) {
        super(new AppState[0]);
        this.gameContext = gameContext;
    }

    @Override
    public void simpleInitApp() {
        // Initialize the globals access so that the default
        // components can find what they need.
        GuiGlobals.initialize(this);
        // Load the 'glass' style
        BaseStyles.loadGlassStyle();
        // Set 'glass' as the default style when not specified
        GuiGlobals.getInstance().getStyles().setDefaultStyle("glass");
    }

    @Override
    public void initialize() {
        super.initialize();
        stateManager.attachAll(gameContext.getBeans(AppState.class));
    }

}
