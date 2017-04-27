package com.etherdungeons.engine.sandbox;

import com.etherdungeons.context.Context;
import com.etherdungeons.context.ContextBuilder;
import com.etherdungeons.engine.commands.CommandDistributor;
import com.etherdungeons.engine.core.Name;
import com.etherdungeons.engine.gameflow.ActiveTurn;
import com.etherdungeons.engine.gameflow.Actor;
import com.etherdungeons.engine.abilities.endturn.EndTurnAbility;
import com.etherdungeons.engine.abilities.endturn.EndTurnAbilitySystem;
import com.etherdungeons.engine.abilities.endturn.EndTurnCommand;
import com.etherdungeons.engine.abilities.endturn.EndTurnCommandHandler;
import com.etherdungeons.engine.gameflow.GameFlowManager;
import com.etherdungeons.engine.gameflow.GameState;
import com.etherdungeons.engine.gameflow.Initiative;
import com.etherdungeons.engine.stats.Health;
import com.etherdungeons.engine.abilities.move.MoveAbility;
import com.etherdungeons.engine.stats.MovePoints;
import com.etherdungeons.engine.position.Position;
import com.etherdungeons.engine.position.map.GameMap;
import com.etherdungeons.engine.abilities.move.MoveAbilitySystem;
import com.etherdungeons.engine.abilities.move.MoveCommand;
import com.etherdungeons.engine.abilities.move.MoveCommandHandler;
import com.etherdungeons.engine.relations.OwnedBy;
import com.etherdungeons.engine.relations.TeamMemberOf;
import com.etherdungeons.engine.stats.ActionPoints;
import com.etherdungeons.engine.stats.CharacterBase;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import com.etherdungeons.entitysystem.EntityDataImpl;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Philipp
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.lang.NoSuchMethodException
     */
    public static void main(String[] args) throws NoSuchMethodException {
        Logger log = LoggerFactory.getLogger(Main.class);
        
        ContextBuilder contextBuilder = new ContextBuilder();
        contextBuilder.add(EntityDataImpl.class.getConstructor());
        contextBuilder.add(GameMap.class.getConstructor());
        contextBuilder.add(CommandDistributor.class.getConstructor(EntityData.class, List.class, Collection.class));
        contextBuilder.add(MoveCommandHandler.class.getConstructor(EntityData.class));
        contextBuilder.add(EndTurnCommandHandler.class.getConstructor(EntityData.class));
        contextBuilder.add(GameFlowManager.class.getConstructor(EntityData.class));
        
        contextBuilder.add(MoveAbilitySystem.class.getConstructor(EntityData.class, GameMap.class));
        contextBuilder.add(EndTurnAbilitySystem.class.getConstructor(EntityData.class, GameFlowManager.class));
        Context context = contextBuilder.build();
        
        context.getBean(GameMap.class).setSize(10, 10);

        EntityData data = context.getBean(EntityData.class);
        
        EntityId gameState = data.createEntity();
        data.set(gameState, new Name("GameState"));
        data.set(gameState, new GameState());
        
        EntityId teamA = data.createEntity();
        data.set(teamA, new Name("TeamA"));
        EntityId teamB = data.createEntity();
        data.set(teamB, new Name("TeamB"));
        
        EntityId amara = data.createEntity();
        data.set(amara, new Name("Amara"));
        data.set(amara, new Actor());
        data.set(amara, new Position(2, 4));
        
        EntityId amaraBase = data.createEntity();
        data.set(amaraBase, new Name("AmaraBase"));
        data.set(amaraBase, new OwnedBy(amara));
        data.set(amaraBase, new CharacterBase());
        data.set(amaraBase, new Initiative(10));
        data.set(amaraBase, new Health(17));
        data.set(amaraBase, new ActionPoints(7));
        data.set(amaraBase, new MovePoints(7));
        data.set(amaraBase, new TeamMemberOf(teamA));
        
        EntityId amaraMove = data.createEntity();
        data.set(amaraMove, new Name("amaraMove"));
        data.set(amaraMove, new OwnedBy(amara));
        data.set(amaraMove, new MoveAbility());
        
        EntityId amaraEndTurn = data.createEntity();
        data.set(amaraEndTurn, new Name("amaraEndTurn"));
        data.set(amaraEndTurn, new OwnedBy(amara));
        data.set(amaraEndTurn, new EndTurnAbility());
        
        EntityId robert = data.createEntity();
        data.set(robert, new Name("Robert"));
        data.set(robert, new Actor());
        data.set(robert, new Position(5, 7));
        
        EntityId robertBase = data.createEntity();
        data.set(robertBase, new Name("RobertBase"));
        data.set(robertBase, new OwnedBy(robert));
        data.set(robertBase, new CharacterBase());
        data.set(robertBase, new Initiative(9));
        data.set(robertBase, new Health(17));
        data.set(robertBase, new ActionPoints(7));
        data.set(robertBase, new MovePoints(7));
        data.set(robertBase, new TeamMemberOf(teamB));
        
        context.getBean(GameFlowManager.class).startGame();
        
        log.info("{}", data.get(amara, Position.class));
        log.info("{}", data.get(amara, MovePoints.class));
        
        context.getBean(CommandDistributor.class).handle(new MoveCommand(new Position(4, 3)));

//        context.getBeans(Runnable.class).stream().forEach(Runnable::run);
        log.info("{}", data.get(amara, Position.class));
        log.info("{}", data.get(amara, MovePoints.class));
        
        context.getBean(CommandDistributor.class).handle(new EndTurnCommand());
        
        log.info("{}", data.get(data.entity(ActiveTurn.class), Name.class).getName());
    }

}
