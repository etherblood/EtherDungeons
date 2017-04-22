package com.etherdungeons.context.definitions;

import com.etherdungeons.context.dependencies.BeanDependency;

/**
 *
 * @author Philipp
 */
public interface BeanDefinition<T> {
    Class<T> getBeanType();
    BeanDependency[] getDependencies();
    T construct(Object... params);
}
