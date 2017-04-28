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

    default <T extends EntityComponent> boolean has(EntityId entity, Class<T> componentClass) {
        return get(entity, componentClass) != null;
    }

    default List<EntityComponent> components(EntityId entity) {
        List<EntityComponent> components = new ArrayList<>();
        for (Class<? extends EntityComponent> componentClass : registeredComponentClasses()) {
            EntityComponent component = get(entity, componentClass);
            if (component != null) {
                components.add(component);
            }
        }
        return components;
    }
}
