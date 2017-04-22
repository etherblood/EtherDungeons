package com.etherdungeons.engine;

import com.etherdungeons.context.Context;
import com.etherdungeons.context.ContextBuilder;
import com.etherdungeons.engine.game.commands.CommandDistributor;
import com.etherdungeons.engine.game.gameflow.ActiveTurn;
import com.etherdungeons.engine.game.gameflow.EndTurnCommand;
import com.etherdungeons.engine.game.gameflow.EndTurnCommandHandler;
import com.etherdungeons.engine.game.gameflow.Initiative;
import com.etherdungeons.engine.game.health.Health;
import com.etherdungeons.engine.game.position.move.MoveAbility;
import com.etherdungeons.engine.game.position.MovePoints;
import com.etherdungeons.engine.game.position.Position;
import com.etherdungeons.engine.game.position.map.GameMap;
import com.etherdungeons.engine.game.position.move.MoveCommand;
import com.etherdungeons.engine.game.position.move.MoveCommandHandler;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.Name;
import com.simsilica.es.base.DefaultEntityData;
import java.util.Collection;

/**
 *
 * @author Philipp
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchMethodException {
        ContextBuilder contextBuilder = new ContextBuilder();
        contextBuilder.add(DefaultEntityData.class.getConstructor());
        contextBuilder.add(GameMap.class.getConstructor());
        contextBuilder.add(CommandDistributor.class.getConstructor(Collection.class));
        contextBuilder.add(MoveCommandHandler.class.getConstructor(EntityData.class, GameMap.class));
        contextBuilder.add(EndTurnCommandHandler.class.getConstructor(EntityData.class));
        Context context = contextBuilder.build();

        EntityData data = context.getBean(EntityData.class);
        
        EntityId amara = data.createEntity();
        data.setComponent(amara, new Name("Amara"));
        data.setComponent(amara, new Initiative(10));
        data.setComponent(amara, new Position(2, 4));
        data.setComponent(amara, new Health(17));
        data.setComponent(amara, new ActiveTurn());
        data.setComponent(amara, new MovePoints(7));
        data.setComponent(amara, new MoveAbility());
        
        EntityId robert = data.createEntity();
        data.setComponent(robert, new Name("Robert"));
        data.setComponent(robert, new Initiative(9));
        data.setComponent(robert, new Position(2, 4));
        data.setComponent(robert, new Health(17));
        data.setComponent(robert, new MovePoints(7));
        
        System.out.println(data.getComponent(amara, Position.class));
        System.out.println(data.getComponent(amara, MovePoints.class));
        
        context.getBean(CommandDistributor.class).handle(new MoveCommand(amara, new Position(4, 3)));

//        context.getBeans(Runnable.class).stream().forEach(Runnable::run);
        System.out.println(data.getComponent(amara, Position.class));
        System.out.println(data.getComponent(amara, MovePoints.class));
        
        context.getBean(CommandDistributor.class).handle(new EndTurnCommand());
        
        System.out.println(data.getComponent(data.findEntity(null, ActiveTurn.class), Name.class).getName());
    }

}
