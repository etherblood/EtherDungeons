package com.etherdungeons.gui.appstates;

import com.etherdungeons.basemod.data.gameflow.MapSize;
import com.etherdungeons.entitysystem.EntityDataReadonly;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;

/**
 *
 * @author Philipp
 */
public class CamAppstate extends AbstractAppState {

    private final EntityDataReadonly data;

    public CamAppstate(EntityDataReadonly data) {
        this.data = data;
        setEnabled(false);
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        MapSize size = data.get(data.entity(MapSize.class), MapSize.class);
        app.getCamera().setLocation(new Vector3f(-5, 14, -5));
        app.getCamera().lookAt(new Vector3f(size.getWidth() / 2, -5, size.getHeight() / 2), Vector3f.UNIT_Y);
    }

}
