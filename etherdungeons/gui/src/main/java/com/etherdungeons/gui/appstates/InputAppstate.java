package com.etherdungeons.gui.appstates;

import com.etherdungeons.basemod.data.position.Position;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

/**
 *
 * @author Philipp
 */
public class InputAppstate extends AbstractAppState {
    
    private final GameControlAppstate gameState;

    public InputAppstate(GameControlAppstate gameState) {
        this.gameState = gameState;
        setEnabled(false);
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        initClickHandler(app);
    }

    private void initClickHandler(Application app) {
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
                    Position clickPosition = toGrid(intersection.x, intersection.z);
                    onPositionClicked(clickPosition);
                }
            }

        }, "leftClick");
    }

    private void onPositionClicked(Position clickPosition) {
        gameState.useAbility(clickPosition);
    }

    private Position toGrid(float x, float z) {
        return new Position((int) x, (int) z);
    }

}
