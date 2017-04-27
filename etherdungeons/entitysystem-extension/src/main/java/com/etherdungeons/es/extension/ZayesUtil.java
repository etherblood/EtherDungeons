package com.etherdungeons.es.extension;

import com.etherdungeons.entitysystem.EntityComponent;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class ZayesUtil {

    @SafeVarargs
    public static void copyExistingComponents(EntityData data, EntityId from, EntityId to, Class<? extends EntityComponent>... componentTypes) {
        for (Class<? extends EntityComponent> componentType : componentTypes) {
            EntityComponent component = data.get(from, componentType);
            if (component != null) {
                data.set(to, component);
            }
        }
    }
    
    @SafeVarargs
    public static void deleteComponents(EntityData data, EntityId entity, Class<? extends EntityComponent>... componentClasses) {
        for (Class<? extends EntityComponent> componentClass : componentClasses) {
            data.remove(entity, componentClass);
        }
    }
}
