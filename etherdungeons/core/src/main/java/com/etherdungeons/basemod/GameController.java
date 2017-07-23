package com.etherdungeons.basemod;

import com.etherdungeons.basemod.actions.Action;
import com.etherdungeons.basemod.actions.ActionManager;
import com.etherdungeons.basemod.data.gameflow.triggers.TriggerRequest;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import java.util.List;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Philipp
 */
public class GameController {

    private final static int MAX_ITERATIONS = 1000;

    private final Logger log;
    private final EntityData data;
    private final ActionManager manager;
    private final List<GameSystem> systems;
    private final ILoggerFactory loggerFactory;

    public GameController(EntityData data, ActionManager manager, List<GameSystem> systems, ILoggerFactory loggerFactory) {
        this.data = data;
        this.manager = manager;
        this.systems = systems;
        this.loggerFactory = loggerFactory;
        log = loggerFactory.getLogger(GameController.class.getName());
    }

    public void startGame() {
        updateGame();
    }

    public boolean applyAction(EntityId actor, Action action) {
        if (manager.isValid(actor, action)) {
            applyValidAction(actor, action);
            return true;
        }
        return false;
    }

    public void applyValidAction(EntityId actor, Action action) {
        assert manager.isValid(actor, action);
        manager.handle(actor, action);
        updateGame();
    }

    public void updateGame() {
        log.debug("start update");
        log.debug("{}", data);
        int iterations = 0;
        do {
            log.debug("start update iteration");
            if (iterations++ > MAX_ITERATIONS) {
                log.error("{}", data);
                throw new IllegalStateException("cancelled update loop after " + MAX_ITERATIONS + " iterations");
            }
            for (GameSystem runnable : systems) {
                if (log.isDebugEnabled()) {
                    String before = data.toString();
                    runnable.run(data, getLogger(runnable));
                    String after = data.toString();
                    if (!before.equals(after)) {
                        log.debug("{} --> {}", runnable, data);
                    }
                } else {
                    runnable.run(data, getLogger(runnable));
                }
            }
            log.debug("end update iteration");
        } while (data.streamEntities(TriggerRequest.class).findAny().isPresent());
        log.debug("end update");
    }

    private Logger getLogger(Object bean) {
        return loggerFactory.getLogger(bean.getClass().getName());
    }
}
