package com.etherdungeons.engine.commands;

import com.etherdungeons.engine.gameflow.triggers.Triggered;
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

    private final Map<Class<? extends Command>, CommandHandler> map;

    public CommandDistributor(Collection<CommandHandler> handlers) {
        map = new HashMap<>();
        for (CommandHandler handler : handlers) {
            map.put(handler.getCommandType(), handler);
        }
    }

    public void handle(Command command) {
        log().info("handling {}", command);
        map.get(command.getClass()).handle(command);
    }
    
    private Logger log() {
        return LoggerFactory.getLogger(getClass());
    }
}
