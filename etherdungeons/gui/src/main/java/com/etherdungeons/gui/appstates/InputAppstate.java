package com.etherdungeons.gui.appstates;

import com.etherdungeons.basemod.data.gameflow.triggers.TriggerRequest;
import com.etherdungeons.basemod.data.position.Position;
import com.etherdungeons.context.Context;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityDataReadonly;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class InputAppstate extends AbstractAppState {

    private final Context context;

    public InputAppstate(Context context) {
        this.context = context;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        InputManager inputManager = app.getInputManager();
        inputManager.addMapping("leftClick", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(new ActionListener() {
            @Override
            public void onAction(String name, boolean isPressed, float tpf) {
                if (isPressed) {
                    Vector2f cursorPosition = inputManager.getCursorPosition();

                    Vector3f source = app.getCamera().getWorldCoordinates(cursorPosition, 0);
                    Vector3f dest = app.getCamera().getWorldCoordinates(cursorPosition, 1);
                    Vector3f dir = dest.subtract(source).normalize();

                    float yPlane = 0;
                    float t = (yPlane - source.y) / dir.y;
                    Vector3f intersection = source.add(dir.mult(t));
                    System.out.println(toGrid(intersection.x, intersection.z));
                }
            }

        }, "leftClick");
        updateGame();
    }

    private void updateGame() {
        List<Runnable> runnables = context.getBeans(Runnable.class);
        EntityData data = context.getBean(EntityData.class);

        do {
            for (Runnable runnable : runnables) {
                runnable.run();
            }
        } while (data.streamEntities(TriggerRequest.class).findAny().isPresent());
    }
    
    private Position toGrid(float x, float z) {
        return new Position((int) x, (int) z);
    }

}
