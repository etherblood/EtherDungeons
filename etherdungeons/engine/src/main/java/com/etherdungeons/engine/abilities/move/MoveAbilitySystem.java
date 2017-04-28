package com.etherdungeons.engine.abilities.move;

import com.etherdungeons.engine.gameflow.ActiveTurn;
import com.etherdungeons.engine.gameflow.triggers.Triggered;
import com.etherdungeons.engine.position.Position;
import com.etherdungeons.engine.position.map.GameMap;
import com.etherdungeons.engine.core.OwnedBy;
import com.etherdungeons.engine.core.Target;
import com.etherdungeons.engine.stats.active.ActiveMovePoints;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class MoveAbilitySystem implements Runnable {

    private final EntityData data;
    private final GameMap map;

    public MoveAbilitySystem(EntityData data, GameMap map) {
        this.data = data;
        this.map = map;
    }

    @Override
    public void run() {
        for (EntityId triggered : data.entities(Triggered.class, Target.class)) {
            EntityId ability = data.get(triggered, Target.class).getTarget();
            if (data.get(ability, MoveAbility.class) != null) {
                EntityId caster = data.get(ability, OwnedBy.class).getOwner();
                if(data.get(caster, ActiveTurn.class) == null) {
                    throw new IllegalStateException();
                }
                Position to = data.get(triggered, Position.class);

                Position from = data.get(caster, Position.class);
                int mp = data.get(caster, ActiveMovePoints.class).getMp();
                List<Position> path = map.findPath(from, to, mp);
                data.set(caster, new ActiveMovePoints((mp - path.size())));
                data.set(caster, to);
            }
        }
    }

}
