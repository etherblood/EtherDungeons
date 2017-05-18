package com.etherdungeons.entitysystem;

/**
 *
 * @author Philipp
 */
public interface EntityData extends EntityDataReadonly {

    <T extends EntityComponent> T set(EntityId entity, T component);

    <T extends EntityComponent> T remove(EntityId entity, Class<T> componentClass);

    EntityId createEntity();

    default void clearEntity(EntityId entity) {
        for (Class<? extends EntityComponent> componentClass : registeredComponentClasses()) {
            remove(entity, componentClass);
        }
    }

    void removeAllComponents(Class<? extends EntityComponent> componentClass);
    
    default void clear() {
        for (Class<? extends EntityComponent> componentClass : registeredComponentClasses()) {
            removeAllComponents(componentClass);
        }
    }
}
