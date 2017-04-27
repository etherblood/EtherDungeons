package com.etherdungeons.entitysystem;

/**
 *
 * @author Philipp
 */
public interface EntityData extends EntityDataReadonly {

    void set(EntityId entity, EntityComponent component);

    void remove(EntityId entity, Class<? extends EntityComponent> componentClass);

    EntityId createEntity();

    default void clearEntity(EntityId entity) {
        for (Class<? extends EntityComponent> componentClass : registeredComponentClasses()) {
            remove(entity, componentClass);
        }
    }
}
