package com.etherdungeons.basemod.actions.implementations;

import com.etherdungeons.basemod.actions.Action;
import com.etherdungeons.basemod.data.position.Position;
import com.etherdungeons.entitysystem.EntityId;
import java.util.Objects;

/**
 *
 * @author Philipp
 */
public class SpiritClawAction implements Action {
    private final EntityId effect;
    private final Position pos;

    public SpiritClawAction(EntityId effect, Position pos) {
        this.effect = effect;
        this.pos = pos;
    }

    public EntityId getEffect() {
        return effect;
    }

    public Position getPosition() {
        return pos;
    }

    @Override
    public String toString() {
        return "SpiritClawAction{" + "effect=" + effect + ", pos=" + pos + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.effect);
        hash = 37 * hash + Objects.hashCode(this.pos);
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
        final SpiritClawAction other = (SpiritClawAction) obj;
        if (!Objects.equals(this.effect, other.effect)) {
            return false;
        }
        return Objects.equals(this.pos, other.pos);
    }
}
