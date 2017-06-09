package com.etherdungeons.modding;

import com.etherdungeons.context.definitions.BeanDefinition;

/**
 *
 * @author Philipp
 */
public class SystemDefinition implements Comparable<SystemDefinition> {
    private final BeanDefinition<?> def;
    private final int orderIndex;

    public SystemDefinition(BeanDefinition<?> def, int orderIndex) {
        this.def = def;
        this.orderIndex = orderIndex;
    }

    public BeanDefinition<?> getBeanDefinition() {
        return def;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    @Override
    public int compareTo(SystemDefinition o) {
        return Integer.compare(orderIndex, o.orderIndex);
    }
}
