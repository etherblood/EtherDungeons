package com.etherdungeons.context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PreDestroy;

/**
 *
 * @author Philipp
 */
public class Context {

    private final List<Object> beans;

    Context(Collection<Object> beans) {
        this.beans = new ArrayList<>(beans);
    }

    public <T> T getBean(Class<T> type) {
        return extractResult(getBeans(type), type);
    }

    public <T> List<T> getBeans(Class<T> type) {
        ArrayList<T> result = new ArrayList<>();
        for (Object candidate : beans) {
            if (type.isInstance(candidate)) {
                result.add((T) candidate);
            }
        }
        return result;
    }

    private <T> T extractResult(List<T> results, Class<T> fieldClass) throws IllegalStateException {
        if (results.size() != 1) {
            throw new IllegalStateException(results.size() + " beans found for " + fieldClass.getName() + ": " + results.toString());
        }
        return results.get(0);
    }

    public void destroy() {
        for (Object bean : beans) {
            for(Class clazz = bean.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
                for (Method declaredMethod : clazz.getDeclaredMethods()) {
                    if (declaredMethod.isAnnotationPresent(PreDestroy.class)) {
                        try {
                            declaredMethod.setAccessible(true);
                            declaredMethod.invoke(bean);
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                            ex.printStackTrace(System.err);
                        }
                    }
                }
            }
        }
        beans.clear();
    }
}
