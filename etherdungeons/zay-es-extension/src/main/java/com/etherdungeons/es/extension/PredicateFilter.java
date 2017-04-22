package com.etherdungeons.es.extension;

import com.simsilica.es.ComponentFilter;
import com.simsilica.es.EntityComponent;
import java.util.function.Predicate;

/**
 *
 * @author Philipp
 */
public class PredicateFilter<T extends EntityComponent> implements ComponentFilter<T> {

    private final Class<T> componentType;
    private final Predicate<T> predicate;

    public PredicateFilter(Class<T> type, Predicate<T> predicate) {
        this.componentType = type;
        this.predicate = predicate;
    }

    @Override
    public Class<T> getComponentType() {
        return componentType;
    }

    @Override
    public boolean evaluate(EntityComponent c) {
        if (!componentType.isInstance(c)) {
            return false;
        }
        return predicate.test((T) c);
    }

}
