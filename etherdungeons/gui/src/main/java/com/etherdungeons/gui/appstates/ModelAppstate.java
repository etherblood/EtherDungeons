package com.etherdungeons.gui.appstates;

import com.etherdungeons.basemod.data.gameflow.Actor;
import com.etherdungeons.basemod.data.position.Position;
import com.etherdungeons.entitysystem.EntityDataReadonly;
import com.etherdungeons.entitysystem.EntityId;
import com.etherdungeons.gui.models.MaterialFactory;
import com.etherdungeons.gui.models.ModelObject;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;

/**
 *
 * @author Philipp
 */
public class ModelAppstate extends AbstractAppState {

    private final EntityDataReadonly data;

    public ModelAppstate(EntityDataReadonly data) {
        this.data = data;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        for (EntityId entity : data.entities(Position.class, Actor.class)) {
            Position pos = data.get(entity, Position.class);
            ModelObject steve = new ModelObject(new MaterialFactory(app.getAssetManager()), app::enqueue, "/Models/steve/skin_default.xml");
            steve.setAnimationName("stand");
            steve.setLocalTranslation(fromGrid(pos));
            steve.setLocalScale(steve.getLocalScale().divide(3));
            ((SimpleApplication) app).getRootNode().attachChild(steve);
        }

    }

    private Vector3f fromGrid(Position pos) {
        return fromGrid(pos.getX(), pos.getY());
    }

    private Vector3f fromGrid(int x, int y) {
        return new Vector3f(x + 0.5f, 0, y + 0.5f);
    }

}
