package com.etherdungeons.engine.commands;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 *
 * @author Philipp
 */
public interface CommandHandler<T extends PlayerCommand> {

    void handle(T command);

    default Class<T> getCommandType() {
        Type[] interfaces = getClass().getGenericInterfaces();
        for (Type genericInterface : interfaces) {
            if (genericInterface instanceof ParameterizedType) {
                ParameterizedType paramType = (ParameterizedType) genericInterface;
                if(paramType.getRawType() == CommandHandler.class) {
                    return (Class<T>) paramType.getActualTypeArguments()[0];
                }
            }
        }
        throw new IllegalStateException();
    }
}
