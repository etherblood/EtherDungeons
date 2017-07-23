package com.etherdungeons.basemod.actions;

import com.etherdungeons.entitysystem.EntityId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Philipp
 */
public class ActionManager {

    private final ILoggerFactory loggerFactory;
    private final Map<Class<? extends Action>, ActionGenerator<? extends Action>> generatorMap;

    public ActionManager(Collection<ActionGenerator> generators, ILoggerFactory loggerFactory) {
        this.loggerFactory = loggerFactory;
        generatorMap = new LinkedHashMap<>();
        for (ActionGenerator generator : generators) {
            generatorMap.put(generator.getActionType(), generator);
        }
    }

    @SuppressWarnings("unchecked")
    public void handle(EntityId actor, Action action) {
        loggerFactory.getLogger(ActionManager.class.getName()).info("handling {}", action);
        ActionGenerator generator = generatorMap.get(action.getClass());
        assert generator.isValid(actor, action);
        generator.executeAction(actor, action);
    }
    
    public boolean isValid(EntityId actor, Action action) {
        ActionGenerator generator = generatorMap.get(action.getClass());
        return generator.isValid(actor, action);
    }
    
    public List<Action> availableActions(EntityId actor) {
        List<Action> list = new ArrayList<>();
        for (ActionGenerator<? extends Action> generator : generatorMap.values()) {
            generator.generateActions(actor, list);
        }
        return list;
    }
}
