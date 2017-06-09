package com.etherdungeons.engine.sandbox;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 *
 * @author Philipp
 */
public class DefaultComponentEqualityDefinition implements ComponentEqualityDefinition {
    public static final DefaultComponentEqualityDefinition SINGLETON = new DefaultComponentEqualityDefinition();
    
    @Override
    public boolean areComponentsEqual(Object componentA, Object componentB) {
        if (componentA.equals(componentB)) {
            return true;
        }
        Class<?> componentClass = componentA.getClass();
        if (componentB.getClass() != componentClass) {
            return false;
        }
        for (Field field : componentClass.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if(!equals(field.get(componentA), field.get(componentB))) {
                    return false;
                }
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (componentClass.getSuperclass() != Object.class) {
            throw new UnsupportedOperationException("components with superclasses not supported by ComponentMapObserver.componentsEqual");
        }
        return true;
    }
    
    private boolean equals(Object a, Object b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (a.getClass().isArray() && b.getClass().isArray()) {
            if (!a.getClass().getComponentType().equals(b.getClass().getComponentType())) {
                return false;
            }
            int length = Array.getLength(a);
            if (Array.getLength(b) != length) {
                return false;
            }
            for (int i = 0; i < length; i++) {
                if (!equals(Array.get(a, i), Array.get(b, i))) {
                    return false;
                }
            }
            return true;
        }
        return a.equals(b);
    }

}

