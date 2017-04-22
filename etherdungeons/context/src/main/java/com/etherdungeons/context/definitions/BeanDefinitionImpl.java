package com.etherdungeons.context.definitions;

import com.etherdungeons.context.dependencies.BeanDependency;
import java.util.Arrays;
import java.util.function.Function;

/**
 *
 * @author Philipp
 */
class BeanDefinitionImpl<T> implements BeanDefinition<T> {

    private final Class<T> beanType;
    private final BeanDependency[] dependencies;
    private final Function<Object[], T> constructorFunction;

    public BeanDefinitionImpl(Class<T> beanType, Function<Object[], T> constructorFunction, BeanDependency... dependencies) {
        this.beanType = beanType;
        this.dependencies = dependencies;
        this.constructorFunction = constructorFunction;
    }

    @Override
    public Class<T> getBeanType() {
        return beanType;
    }

    @Override
    public BeanDependency[] getDependencies() {
        return dependencies;
    }

    @Override
    public T construct(Object... params) {
        return constructorFunction.apply(params);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{beanType=" + beanType + ", dependencies=" + Arrays.toString(dependencies) + ", constructorFunction=" + constructorFunction + '}';
    }

}
