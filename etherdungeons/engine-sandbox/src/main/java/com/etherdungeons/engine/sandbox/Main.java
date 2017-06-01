package com.etherdungeons.engine.sandbox;

import com.etherdungeons.context.Context;
import com.etherdungeons.engine.actions.Action;
import com.etherdungeons.engine.actions.ActionManager;
import com.etherdungeons.engine.actions.implementations.EndTurnAction;
import com.etherdungeons.engine.actions.implementations.MoveAction;
import com.etherdungeons.engine.actions.implementations.SpiritClawAction;
import com.etherdungeons.engine.data.position.map.GameMap;
import com.etherdungeons.engine.data.gameflow.triggers.TriggerRequest;
import com.etherdungeons.engine.data.position.Position;
import com.etherdungeons.entitysystem.EntityComponent;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import com.etherdungeons.entitysystem.EntityDataImpl;
import com.etherdungeons.entitysystem.EntityDataReadonly;
import com.etherdungeons.templates.TemplateImporter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
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
    public static void main(String[] args) throws Exception {
        Logger log = LoggerFactory.getLogger(Main.class);
        
        TemplateImporter importer = new Setup().createImporter();
        for (String file : Arrays.asList("maps", "characters", "abilities")) {
            try(Reader reader = new InputStreamReader(Main.class.getResourceAsStream("/templates/" + file + ".json"))) {
                importer.readTemplate(reader);
            }
        }
        
        Context context = new Setup().createContext();
        context.getBean(GameMap.class).setSize(10, 10);

        List<Object> mapParams = new ArrayList<>();
        
        EntityData data = context.getBean(EntityData.class);
        EntityId amara = data.createEntity();
        EntityId amaraEndTurn = data.createEntity();
        EntityId amaraMove = data.createEntity();
        EntityId amaraSpiritClaw = data.createEntity();
        EntityId amaraRegenerate = data.createEntity();
        importer.applyTemplate(data, "amara", amara, amaraEndTurn, amaraMove, amaraSpiritClaw, amaraRegenerate);
        mapParams.add(amara);

        EntityId firstRobert = null;
        for (int i = 0; i < 3; i++) {
            EntityId robert = data.createEntity();
            if(firstRobert == null) {
                firstRobert = robert;
            }
            importer.applyTemplate(data, "robert", robert);
            mapParams.add(robert);
        }
        importer.applyTemplate(data, "test_map", mapParams.toArray());
        for (EntityId entity : data.entities(Position.class)) {
            context.getBean(GameMap.class).add(entity, data.get(entity, Position.class));
        }

        update(context, log);
        handleCommand(context, amara, new MoveAction(amaraMove, new Position(2, 3)), log);
        handleCommand(context, amara, new MoveAction(amaraMove, new Position(2, 4)), log);
        handleCommand(context, amara, new MoveAction(amaraMove, new Position(2, 5)), log);
        handleCommand(context, amara, new SpiritClawAction(amaraSpiritClaw, new Position(2, 7)), log);
//        logState(data, log);
        handleCommand(context, amara, new EndTurnAction(amaraEndTurn), log);
//        logState(data, log);
        log.info("{}", context.getBean(ActionManager.class).availableActions(firstRobert));
//        handleCommand(context, new EndTurnCommand(robertEndTurn), log);
////        logState(data, log);
    }

    private static void logState(EntityData data, Logger log) {
        Map<EntityId, Collection<String>> map = new HashMap<>();
        for (Class<? extends EntityComponent> componentClass : data.registeredComponentClasses()) {
            for (EntityId entity : data.entities(componentClass)) {
                map.computeIfAbsent(entity, e -> new TreeSet<>()).add(data.get(entity, componentClass).toString());
            }
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        log.info("state:\n{}", gson.toJson(map));
    }

    private static void handleCommand(Context context, EntityId actor, Action command, Logger log) {
        ActionManager actionManager = context.getBean(ActionManager.class);
        if(!actionManager.isValid(actor, command)) {
            throw new UnsupportedOperationException(command.toString());
        }
        actionManager.handle(actor, command);
        update(context, log);
    }

    private static void update(Context context, Logger log) {
        List<Runnable> runnables = context.getBeans(Runnable.class);
        EntityData data = context.getBean(EntityData.class);

        EntityDataImpl entityDataImpl = new EntityDataImpl();
        do {
            copyState(data, entityDataImpl);
            for (Runnable runnable : runnables) {
                try {
                    runnable.run();
                } catch (Throwable t) {
                    log.info("{}", runnables);
                    logState(data, log);
                    throw t;
                }
            }
            System.out.flush();
            System.out.println("differences from last iteration: ");
            printDiff(entityDataImpl, data, !data.streamEntities(TriggerRequest.class).findAny().isPresent());
            System.out.flush();
        } while (data.streamEntities(TriggerRequest.class).findAny().isPresent());
        System.out.println("finished update");
        System.out.flush();
    }
    
    private static void copyState(EntityDataReadonly source, EntityData dest) {
        dest.clear();
        for (Class<? extends EntityComponent> componentClass : source.registeredComponentClasses()) {
            for (EntityId entity : source.entities(componentClass)) {
                dest.set(entity, source.get(entity, componentClass));
            }
        }
    }
    
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static void printDiff(EntityDataReadonly prev, EntityDataReadonly next, boolean printUnchanged) {
        Set<EntityId> prevEntities = new HashSet<>(prev.entities());
        Set<EntityId> nextEntities = new HashSet<>(next.entities());
        
        Set<EntityId> union = new HashSet<>(prevEntities);
        union.addAll(nextEntities);
        
        Set<Class<? extends EntityComponent>> componentClasses = new HashSet<>(prev.registeredComponentClasses());
        componentClasses.addAll(next.registeredComponentClasses());
        
        for (EntityId entity : union) {
            boolean entityPrinted = false;
            for (Class<? extends EntityComponent> componentClass : componentClasses) {
                Object prevComp = prev.get(entity, componentClass);
                Object nextComp = next.get(entity, componentClass);
                String componentLine = null;
                if(prevComp != null) {
                    if(nextComp != null) {
                        if(!componentsEqual(prevComp, nextComp)) {
                            componentLine = "\t" + ANSI_BLUE + prevComp + " -> " + nextComp;
                        } else if(printUnchanged) {
                            componentLine = "\t" + ANSI_RESET + nextComp;
                        }
                    } else {
                        componentLine = "\t" + ANSI_RED + prevComp;
                    }
                } else {
                    if(nextComp != null) {
                        componentLine = "\t" + ANSI_GREEN + nextComp;
                    }
                }
                if(componentLine != null) {
                    if(!entityPrinted) {
                        System.out.println(entity);
                        entityPrinted = true;
                    }
                    System.out.println(componentLine);
                }
            }
            if(entityPrinted) {
                System.out.println(ANSI_RESET);
            }
        }
    }

    private static boolean componentsEqual(Object prevComp, Object nextComp) {
        return DefaultComponentEqualityDefinition.SINGLETON.areComponentsEqual(prevComp, nextComp);
    }

}
