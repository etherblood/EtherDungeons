package com.etherdungeons.context.definitions;

import com.etherdungeons.context.dependencies.BeanDependency;
import com.etherdungeons.context.dependencies.BeanDependencyFactory;
import com.etherdungeons.util.LambdaExceptionUtil;
import java.lang.reflect.Constructor;
import java.util.function.Function;

/**
 *
 * @author Philipp
 */
public class BeanDefinitionFactory {

    public static <T> BeanDefinition<T> of(Class<T> c) {
        Constructor<?>[] constructors = c.getConstructors();
        if (constructors.length != 1) {
            throw new IllegalArgumentException(c.getName());
        }
        return of((Constructor<T>) constructors[0]);
    }

    public static <T> BeanDefinition<T> of(Constructor<T> constructor) {
        BeanDependency[] dependencies = new BeanDependency[constructor.getParameterCount()];
        for (int i = 0; i < dependencies.length; i++) {
            dependencies[i] = BeanDependencyFactory.of(constructor.getGenericParameterTypes()[i], constructor.getParameterTypes()[i]);
        }
        return of_(constructor.getDeclaringClass(), LambdaExceptionUtil.rethrowFunction(constructor::newInstance), dependencies);
    }

    public static <T> BeanDefinition<T> of(Class<T> beanType, Function<Object[], T> constructorFunction, Class... types) {
        BeanDependency[] dependencies = new BeanDependency[types.length];
        for (int i = 0; i < dependencies.length; i++) {
            dependencies[i] = BeanDependencyFactory.of(types[i]);
        }
        return of_(beanType, constructorFunction, dependencies);
    }

    public static <T> BeanDefinition<T> of_(Class<T> beanType, Function<Object[], T> constructorFunction, BeanDependency... dependencies) {
        return new BeanDefinitionImpl(beanType, constructorFunction, dependencies);
    }
}
