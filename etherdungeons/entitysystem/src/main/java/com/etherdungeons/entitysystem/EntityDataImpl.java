package com.etherdungeons.entitysystem;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Philipp
 */
public class EntityDataImpl implements EntityData, EntityDataReadonly {

    private final HashMap<Class<? extends EntityComponent>, HashMap<EntityId, EntityComponent>> componentMaps = new HashMap<>();
    private final EntityIdFactory factory;
    private final boolean useAutosort;
    private final Comparator<Class<? extends EntityComponent>> classComparator = Comparator.comparingInt(c -> getComponentMap(c).size());

    public EntityDataImpl() {
        this(new IncrementalEntityIdFactory());
    }

    public EntityDataImpl(EntityIdFactory factory) {
        this(factory, true);
    }

    public EntityDataImpl(EntityIdFactory factory, boolean useAutosort) {
        this.factory = factory;
        this.useAutosort = useAutosort;
    }

    private HashMap<EntityId, EntityComponent> getComponentMap(Class<? extends EntityComponent> componentClass) {
        return componentMaps.computeIfAbsent(componentClass, c -> new HashMap<>());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends EntityComponent> T get(EntityId entity, Class<T> componentClass) {
        return (T) getComponentMap(componentClass).get(entity);
    }

    @Override
    public <T extends EntityComponent> boolean has(EntityId entity, Class<T> componentClass) {
        return getComponentMap(componentClass).containsKey(entity);
    }

    @Override
    public void set(EntityId entity, EntityComponent component) {
        getComponentMap(component.getClass()).put(entity, component);
    }

    @Override
    public void remove(EntityId entity, Class<? extends EntityComponent> componentClass) {
        getComponentMap(componentClass).remove(entity);
    }

    @Override
    public Stream<EntityId> streamEntities(Class<?>... componentTypes) {
        @SuppressWarnings("unchecked")
        Class<? extends EntityComponent>[] types = (Class<? extends EntityComponent>[]) componentTypes;
        switch (componentTypes.length) {
            case 0:
                return componentMaps.values().stream().flatMap(m -> m.keySet().stream()).distinct();
            case 1:
                return getComponentMap(types[0]).keySet().stream();
            default:
                if (useAutosort) {
                    Arrays.sort(types, classComparator);
                }
                return getComponentMap(types[0]).keySet().stream().filter(id -> filter(id, types));
        }
    }

    private boolean filter(EntityId id, Class<? extends EntityComponent>[] componentTypes) {
        for (int i = 1; i < componentTypes.length; i++) {
            if (!getComponentMap(componentTypes[i]).containsKey(id)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Set<Class<? extends EntityComponent>> registeredComponentClasses() {
        return componentMaps.keySet();
    }

    @Override
    public EntityId createEntity() {
        return factory.createEntity();
    }
}
