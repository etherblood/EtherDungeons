package com.etherdungeons.engine.game.commands;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Philipp
 */
public class CommandDistributor {

    private final Map<Class<? extends PlayerCommand>, CommandHandler> map;

    public CommandDistributor(Collection<CommandHandler> handlers) {
        map = new HashMap<>();
        for (CommandHandler handler : handlers) {
            map.put(handler.getCommandType(), handler);
        }
    }

    public void handle(PlayerCommand command) {
        System.out.println("handling " + command);
        map.get(command.getClass()).handle(command);
    }
}
