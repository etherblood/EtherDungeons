package com.etherdungeons.engine.actions;

import com.etherdungeons.entitysystem.EntityId;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philipp
 */
public interface ActionGenerator<A extends Action> {
    void executeAction(EntityId actor, A action);
    void generateActions(EntityId actor, List<Action> actions);
    default boolean isValid(EntityId actor, A action) {
        List<Action> list = new ArrayList<>();
        generateActions(actor, list);
        return list.contains(action);
    }
    @SuppressWarnings("unchecked")
    default Class<A> getActionType() {
        Type[] interfaces = getClass().getGenericInterfaces();
        for (Type genericInterface : interfaces) {
            if (genericInterface instanceof ParameterizedType) {
                ParameterizedType paramType = (ParameterizedType) genericInterface;
                if(paramType.getRawType() == ActionGenerator.class) {
                    return (Class<A>) paramType.getActualTypeArguments()[0];
                }
            }
        }
        throw new IllegalStateException();
    }
}
