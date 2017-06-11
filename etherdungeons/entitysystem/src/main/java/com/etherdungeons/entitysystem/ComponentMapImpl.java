package com.etherdungeons.entitysystem;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


public class ComponentMapImpl<T extends EntityComponent> implements ComponentMap<T> {
    private final Map<EntityId, T> map = new LinkedHashMap<>();

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public T get(EntityId entity) {
        return map.get(entity);
    }

    @Override
    public boolean has(EntityId entity) {
        return map.containsKey(entity);
    }

    @Override
    public T set(EntityId entity, T component) {
        return map.put(entity, component);
    }

    @Override
    public T remove(EntityId entity) {
        return map.remove(entity);
    }

    @Override
    public Set<EntityId> entities() {
        return map.keySet();
    }

    @Override
    public void clear() {
        map.clear();
    }
}
