package com.etherdungeons.engine.commands;

import com.etherdungeons.engine.gameflow.Triggered;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Philipp
 */
public class CommandDistributor {

    private final EntityData data;
    private final Map<Class<? extends PlayerCommand>, CommandHandler> map;
    private final List<Runnable> runnables;

    public CommandDistributor(EntityData data, List<Runnable> runnables, Collection<CommandHandler> handlers) {
        this.data = data;
        this.runnables = runnables;
        map = new HashMap<>();
        for (CommandHandler handler : handlers) {
            map.put(handler.getCommandType(), handler);
        }
    }

    public void handle(PlayerCommand command) {
        log().info("handling {}", command);
        map.get(command.getClass()).handle(command);
        postCommand(command);
    }

    private void postCommand(PlayerCommand command) {
        log().info("postCommand update for {}", command);
        Set<EntityId> entities = data.entities(Triggered.class);
        while (!entities.isEmpty()) {
            for (Runnable runnable : runnables) {
                runnable.run();
            }
            Set<EntityId> next = data.entities(Triggered.class);
            entities.retainAll(next);
            if(!entities.isEmpty()) {
                log().error("{}", entities);
                throw new IllegalStateException();
            }
            entities = next;
        }
    }
    
    private Logger log() {
        return LoggerFactory.getLogger(getClass());
    }
}
