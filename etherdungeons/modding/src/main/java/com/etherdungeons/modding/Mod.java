package com.etherdungeons.modding;

import com.etherdungeons.context.definitions.BeanDefinition;
import java.util.Collection;

/**
 *
 * @author Philipp
 */
public interface Mod {

    Collection<BeanDefinition<?>> getBeanDefinitions();
    Collection<OrderConstraint> getOrderConstraints();
}
