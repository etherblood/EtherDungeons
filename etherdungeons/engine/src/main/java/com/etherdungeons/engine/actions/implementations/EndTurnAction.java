package com.etherdungeons.engine.actions.implementations;

import com.etherdungeons.engine.actions.Action;
import com.etherdungeons.entitysystem.EntityId;
import java.util.Objects;

/**
 *
 * @author Philipp
 */
public class EndTurnAction implements Action {
    private final EntityId endTurnEffect;

    public EndTurnAction(EntityId endTurnEffect) {
        this.endTurnEffect = endTurnEffect;
    }

    public EntityId getEndTurnEffect() {
        return endTurnEffect;
    }

    @Override
    public String toString() {
        return "EndTurnAction{" + "endTurnEffect=" + endTurnEffect + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.endTurnEffect);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EndTurnAction other = (EndTurnAction) obj;
        return Objects.equals(this.endTurnEffect, other.endTurnEffect);
    }
}
