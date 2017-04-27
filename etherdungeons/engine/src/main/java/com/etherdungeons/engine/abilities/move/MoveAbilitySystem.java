package com.etherdungeons.engine.abilities.move;

import com.etherdungeons.engine.gameflow.ActiveTurn;
import com.etherdungeons.engine.gameflow.Triggered;
import com.etherdungeons.engine.position.Position;
import com.etherdungeons.engine.position.map.GameMap;
import com.etherdungeons.engine.relations.OwnedBy;
import com.etherdungeons.engine.stats.MovePoints;
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
        for (EntityId triggered : data.entities(Triggered.class)) {
            EntityId ability = data.get(triggered, Triggered.class).getEntity();
            if (data.get(ability, MoveAbility.class) != null) {
                EntityId caster = data.get(ability, OwnedBy.class).getOwner();
                if(data.get(caster, ActiveTurn.class) == null) {
                    throw new IllegalStateException();
                }
                Position to = data.get(triggered, Position.class);

                Position from = data.get(caster, Position.class);
                int mp = data.get(caster, MovePoints.class).getMp();
                List<Position> path = map.findPath(from, to, mp);
                data.set(caster, new MovePoints((mp - path.size())));
                data.set(caster, to);
                
                data.clearEntity(triggered);
            }
        }
    }

}
