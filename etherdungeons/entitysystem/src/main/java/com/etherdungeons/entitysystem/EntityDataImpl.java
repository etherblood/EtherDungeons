package com.etherdungeons.entitysystem;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author Philipp
 */
public class EntityDataImpl implements EntityData, EntityDataReadonly {

    private final HashMap<Class<? extends EntityComponent>, ComponentMap<EntityComponent>> componentMaps = new LinkedHashMap<>();
    private final Function<Class<? extends EntityComponent>, ComponentMap<EntityComponent>> componentMapFactory;
    private final Supplier<EntityId> entityIdFactory;
    private final boolean useAutosort;
    private final Comparator<Class<? extends EntityComponent>> classComparator = Comparator.comparingInt(c -> getComponentMap(c).size());

    public EntityDataImpl() {
        this(new IncrementalEntityIdFactory()::createEntity);
    }

    public EntityDataImpl(Supplier<EntityId> factory) {
        this(factory, true);
    }

    public EntityDataImpl(Supplier<EntityId> factory, boolean useAutosort) {
        this(factory, c -> new ComponentMapImpl<>(), useAutosort);
    }

    public EntityDataImpl(Supplier<EntityId> factory, Function<Class<? extends EntityComponent>, ComponentMap<EntityComponent>> componentMapFactory, boolean useAutosort) {
        this.entityIdFactory = factory;
        this.componentMapFactory = componentMapFactory;
        this.useAutosort = useAutosort;
    }

    private ComponentMap<EntityComponent> getComponentMap(Class<? extends EntityComponent> componentClass) {
        return componentMaps.computeIfAbsent(componentClass, componentMapFactory);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends EntityComponent> T get(EntityId entity, Class<T> componentClass) {
        return (T) getComponentMap(componentClass).get(entity);
    }

    @Override
    public boolean has(EntityId entity, Class<? extends EntityComponent> componentClass) {
        return getComponentMap(componentClass).has(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends EntityComponent> T set(EntityId entity, T component) {
        return (T) getComponentMap(component.getClass()).set(entity, component);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends EntityComponent> T remove(EntityId entity, Class<T> componentClass) {
        return (T) getComponentMap(componentClass).remove(entity);
    }

    @Override
    public Stream<EntityId> streamEntities(Class<?>... componentTypes) {
        @SuppressWarnings("unchecked")
        Class<? extends EntityComponent>[] types = (Class<? extends EntityComponent>[]) componentTypes;
        switch (componentTypes.length) {
            case 0:
                return componentMaps.values().stream().flatMap(m -> m.entities().stream()).distinct();
            case 1:
                return getComponentMap(types[0]).entities().stream();
            default:
                if (useAutosort) {
                    Arrays.sort(types, classComparator);
                }
                return getComponentMap(types[0]).entities().stream().filter(id -> filter(id, types));
        }
    }

    private boolean filter(EntityId id, Class<? extends EntityComponent>[] componentTypes) {
        for (int i = 1; i < componentTypes.length; i++) {
            if (!getComponentMap(componentTypes[i]).has(id)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void removeAllComponents(Class<? extends EntityComponent> componentClass) {
        getComponentMap(componentClass).clear();
    }

    @Override
    public Set<Class<? extends EntityComponent>> registeredComponentClasses() {
        return componentMaps.keySet();
    }

    @Override
    public EntityId createEntity() {
        return entityIdFactory.get();
    }
}
