package com.etherdungeons.context.dependencies;

import com.etherdungeons.util.CustomCollectors;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 *
 * @author Philipp
 */
public class BeanDependencyFactory {

    public static BeanDependency of(Type genericType, Class<?> rawType) {
        if (rawType.isArray()) {
            return ofArray(rawType);
        } else if (Collection.class.isAssignableFrom(rawType)) {
            return ofCollection((ParameterizedType) genericType, rawType);
        }
        return of(rawType);
    }

    private static BeanDependency ofCollection(ParameterizedType type, Class<?> rawType) {
        Class<?> itemType = (Class) type.getActualTypeArguments()[0];
        if (Set.class.isAssignableFrom(rawType)) {
            return of(itemType, Collectors.toSet());
        }
        return of(itemType, Collectors.toList());
    }

    private static BeanDependency ofArray(Class<?> type) {
        Class<?> itemType = type.getComponentType();
        return of(itemType, CustomCollectors.arrayCollector(itemType));
    }

    public static BeanDependency of(Class<?> rawType) {
        return of(rawType, CustomCollectors.singletonCollector());
    }

    public static BeanDependency of(Class<?> type, Collector<?, ?, ?> collector) {
        return new BeanDependencyImpl(type, collector);
    }
}
