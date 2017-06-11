package com.etherdungeons.entitysystem;

import java.util.Set;

/**
 *
 * @author Philipp
 */
public interface ComponentMap<T extends EntityComponent> {

    public T get(EntityId entity);
    public T set(EntityId entity, T component);
    public T remove(EntityId entity);
    public Set<EntityId> entities();
    public void clear();
    public default boolean has(EntityId entity) {
        return get(entity) != null;
    }
    public default int size() {
        return entities().size();
    }

}
