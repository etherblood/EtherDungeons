package com.etherdungeons.gui.appstates;

import com.etherdungeons.basemod.GameSystem;
import com.etherdungeons.basemod.data.gameflow.Actor;
import com.etherdungeons.basemod.data.gameflow.effects.move.MoveEffect;
import com.etherdungeons.basemod.data.gameflow.triggers.Triggered;
import com.etherdungeons.basemod.data.gameflow.triggers.triggerargs.TriggerArgsTargets;
import com.etherdungeons.basemod.data.position.Position;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityDataReadonly;
import com.etherdungeons.entitysystem.EntityId;
import com.etherdungeons.gui.models.MaterialFactory;
import com.etherdungeons.gui.models.ModelObject;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Philipp
 */
public class ModelAppstate extends BaseAppState implements GameSystem {

    private final EntityDataReadonly data;
    private final Map<EntityId, ModelObject> map = new HashMap<>();

    public ModelAppstate(EntityDataReadonly data) {
        this.data = data;
        setEnabled(false);
    }

    @Override
    public void run(EntityData data) {
        for (EntityId triggerArgs : data.entities(Triggered.class)) {
            EntityId effect = data.get(triggerArgs, Triggered.class).getTrigger();
            MoveEffect move = data.get(effect, MoveEffect.class);
            if(move != null) {
                TriggerArgsTargets targets = data.get(triggerArgs, TriggerArgsTargets.class);
//                if(targets != null) {
                    for (EntityId target : targets.getTargets()) {
//                        if(map.containsKey(target)) {
                            map.get(target).setLocalTranslation(fromGrid(data.get(triggerArgs, Position.class)));
//                        }
                    }
//                }
            }
        }
    }

    private Vector3f fromGrid(Position pos) {
        return fromGrid(pos.getX(), pos.getY());
    }

    private Vector3f fromGrid(int x, int y) {
        return new Vector3f(x + 0.5f, 0, y + 0.5f);
    }

    @Override
    protected void initialize(Application app) {
    }

    @Override
    protected void cleanup(Application app) {
    }

    @Override
    protected void onEnable() {
        for (EntityId entity : data.entities(Position.class, Actor.class)) {
            Position pos = data.get(entity, Position.class);
            ModelObject steve = new ModelObject(new MaterialFactory(getApplication().getAssetManager()), getApplication()::enqueue, "/Models/steve/skin_default.xml");
            steve.setAnimationName("stand");
            steve.setLocalTranslation(fromGrid(pos));
            steve.setLocalScale(steve.getLocalScale().divide(3));
            ((SimpleApplication) getApplication()).getRootNode().attachChild(steve);
            map.put(entity, steve);
        }
    }

    @Override
    protected void onDisable() {
    }

}
