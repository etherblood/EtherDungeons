package com.etherdungeons.gui.appstates;

import com.etherdungeons.basemod.GameController;
import com.etherdungeons.basemod.actions.Action;
import com.etherdungeons.basemod.actions.implementations.EndTurnAction;
import com.etherdungeons.basemod.actions.implementations.MoveAction;
import com.etherdungeons.basemod.actions.implementations.SpiritClawAction;
import com.etherdungeons.basemod.data.gameflow.effects.Ability;
import com.etherdungeons.basemod.data.gameflow.effects.move.MoveEffect;
import com.etherdungeons.basemod.data.gameflow.effects.spiritclaw.SpiritClawEffect;
import com.etherdungeons.basemod.data.gameflow.effects.turnflow.EndTurnEffect;
import com.etherdungeons.basemod.data.gameflow.effects.turnflow.phases.ActiveTurn;
import com.etherdungeons.basemod.data.position.Position;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import java.util.stream.Collectors;

/**
 *
 * @author Philipp
 */
public class GameControlAppstate extends BaseAppState {

    private final GameController gameController;
    private final EntityData data;
    private EntityId selectedAbility;
    private final SkillbarAppstate skillAppstate;

    public GameControlAppstate(EntityData data, GameController gameController, SkillbarAppstate skillAppstate) {
        this.gameController = gameController;
        this.data = data;
        this.skillAppstate = skillAppstate;
        setEnabled(false);
    }
    
    public void selectAbility(EntityId ability) {
        selectedAbility = ability;
        if(data.has(ability, EndTurnEffect.class)) {
            Action action = new EndTurnAction(ability);
            tryAction(ability, action);
        }
    }

    void useAbility(Position clickPosition) {
        if(data.has(selectedAbility, MoveEffect.class)) {
            Action action = new MoveAction(selectedAbility, clickPosition);
            tryAction(selectedAbility, action);
        } else if(data.has(selectedAbility, SpiritClawEffect.class)) {
            Action action = new SpiritClawAction(selectedAbility, clickPosition);
            tryAction(selectedAbility, action);
        } else {
            System.out.println("unknown ability: " + data.components(selectedAbility).stream().map(Object::toString).collect(Collectors.joining(", ")));
        }
    }

    private void tryAction(EntityId ability, Action action) {
        EntityId actor = data.get(ability, Ability.class).getOwner();
        if(gameController.applyAction(actor, action)) {
            skillAppstate.refreshSkills();
            selectCurrentMoveAbility();
        } else {
            System.out.println("invalid action: " + action + " for actor: " + actor);
        }
    }

    private void selectCurrentMoveAbility() {
        selectedAbility = data.streamEntities(Ability.class, MoveEffect.class)
                .filter(e -> data.get(e, Ability.class).getOwner().equals(data.entity(ActiveTurn.class)))
                .findAny().get();
    }

    @Override
    protected void initialize(Application app) {
    }

    @Override
    protected void cleanup(Application app) {
    }

    @Override
    protected void onEnable() {
    }

    @Override
    protected void onDisable() {
    }

    void startGame() {
        setEnabled(true);
        gameController.startGame();
        getStateManager().getState(CamAppstate.class).setEnabled(true);
        getStateManager().getState(GridAppstate.class).setEnabled(true);
        getStateManager().getState(InputAppstate.class).setEnabled(true);
        getStateManager().getState(ModelAppstate.class).setEnabled(true);
        getStateManager().getState(SkillbarAppstate.class).setEnabled(true);
        getStateManager().getState(LobbyAppstate.class).setEnabled(false);
    }
}
