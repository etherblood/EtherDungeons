package com.etherdungeons.engine.effects.move;

import com.etherdungeons.engine.gameflow.triggers.Triggered;
import com.etherdungeons.engine.position.Position;
import com.etherdungeons.engine.position.map.GameMap;
import com.etherdungeons.engine.core.Target;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class MoveSystem implements Runnable {

    private final EntityData data;
    private final GameMap map;

    public MoveSystem(EntityData data, GameMap map) {
        this.data = data;
        this.map = map;
    }

    @Override
    public void run() {
        for (EntityId triggered : data.entities(Triggered.class)) {
            EntityId ability = data.get(triggered, Triggered.class).getTrigger();
            if (data.hasAll(ability, MoveTrigger.class, Target.class)) {
//                EntityId caster = data.get(ability, OwnedBy.class).getOwner();
//                if(data.get(caster, ActiveTurn.class) == null) {
//                    throw new IllegalStateException();
//                }
                EntityId target = data.get(ability, Target.class).getTarget();
                Position from = data.get(target, Position.class);
                Position to = data.get(triggered, Position.class);
//
//                int mp = data.get(caster, ActiveMovePoints.class).getMp();
//                if(mp <= 0) {
//                    throw new IllegalStateException(Integer.toString(mp));
//                }
//                if(PositionUtil.manhattanDistance(from, to) != 1) {
//                    throw new IllegalStateException(Integer.toString(PositionUtil.manhattanDistance(from, to)));
//                }
////                List<Position> path = map.findPath(from, to, mp);
//                data.set(caster, new ActiveMovePoints(mp - 1));
                data.set(target, to);
                map.remove(from);
                map.add(target, to);
            }
        }
    }

}
