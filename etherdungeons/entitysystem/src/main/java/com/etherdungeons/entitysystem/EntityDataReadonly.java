package com.etherdungeons.entitysystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Philipp
 */
public interface EntityDataReadonly {

    <T extends EntityComponent> T get(EntityId entity, Class<T> componentClass);

    Stream<EntityId> streamEntities(Class<?>... componentTypes);

    Set<Class<? extends EntityComponent>> registeredComponentClasses();

    default Set<EntityId> entities(Class<?>... componentTypes) {
        return streamEntities(componentTypes).collect(Collectors.toSet());
    }

    default EntityId entity(Class<?>... componentTypes) {
        return streamEntities(componentTypes).findAny().orElse(null);
    }

    default boolean has(EntityId entity, Class<? extends EntityComponent> componentClass) {
        return get(entity, componentClass) != null;
    }

    default boolean hasAll(EntityId entity, Class<? extends EntityComponent>[] componentClasses) {
        for (Class<? extends EntityComponent> componentClass : componentClasses) {
            if(!has(entity, componentClass)) {
                return false;
            }
        }
        return true;
    }
    
    default boolean isEmpty(EntityId entity) {
        return !streamComponents(entity).findAny().isPresent();
    }

    default List<EntityComponent> components(EntityId entity) {
        return streamComponents(entity).collect(Collectors.toList());
    }

    default Stream<? extends EntityComponent> streamComponents(EntityId entity) {
        return registeredComponentClasses().stream().map(type -> get(entity, type)).filter(comp -> comp != null);
    }
}
