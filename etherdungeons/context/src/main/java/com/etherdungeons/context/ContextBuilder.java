package com.etherdungeons.context;

import com.etherdungeons.context.definitions.BeanDefinition;
import com.etherdungeons.context.definitions.BeanDefinitionFactory;
import com.etherdungeons.context.dependencies.BeanDependency;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;

/**
 *
 * @author Philipp
 */
public class ContextBuilder {

    private final List<BeanDefinition> definitions = new ArrayList<>();

    public void add(Constructor constructor) {
        add(BeanDefinitionFactory.of(constructor));
    }
    
    public void add(BeanDefinition def) {
        definitions.add(def);
    }

    public Context build() {
        Object[] beans = new Object[definitions.size()];
        List<BeanDefinition> instantiationOrder = instantiationOrder();
        List<Object> beansList = Arrays.asList(beans);
        for (BeanDefinition def : instantiationOrder) {
            beans[definitions.indexOf(def)] = def.construct(findParams(beansList, def.getDependencies()));
        }

        Context context = new Context(beansList);
        for (Object bean : beans) {
            postConstruct(bean);
        }
        return context;
    }

    protected List<BeanDefinition> instantiationOrder() {
        List<BeanDefinition> result = new ArrayList<>(definitions.size());
        List<BeanDefinition> open = new ArrayList<>(definitions);

        boolean repeat;
        do {
            repeat = false;
            Iterator<BeanDefinition> iterator = open.iterator();
            while (iterator.hasNext()) {
                BeanDefinition def = iterator.next();
                if (Stream.of(def.getDependencies()).noneMatch(d -> containsDependency(open, d))) {
                    result.add(def);
                    iterator.remove();
                    repeat = true;
                }
            }
        } while (repeat);
        if (!open.isEmpty()) {
            throw new IllegalStateException("following beans could not be instantiated: " + open.stream().map(Object::toString).collect(Collectors.joining(", ")));
        }
        return result;
    }

    private boolean containsDependency(List<BeanDefinition> list, BeanDependency dependency) {
        Class<?> dependencyType = dependency.getDependencyType();
        for (BeanDefinition def : list) {
            Class beanType = def.getBeanType();
            if (dependencyType.isAssignableFrom(beanType)) {
                return true;
            }
        }
        return false;
    }

    private Object[] findParams(List<Object> beans, BeanDependency[] types) {
        Object[] params = new Object[types.length];
        for (int i = 0; i < types.length; i++) {
            params[i] = findParam(beans, types[i]);
        }
        return params;
    }

    private Object findParam(List<Object> beans, BeanDependency dependency) {
        return dependency.paramFromCandidates(beans.stream().filter(dependency.getDependencyType()::isInstance));
    }

    private void postConstruct(Object bean) {
        for (Method method : bean.getClass().getMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                try {
                    method.invoke(bean);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
