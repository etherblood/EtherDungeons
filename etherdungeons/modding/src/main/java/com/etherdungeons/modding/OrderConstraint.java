package com.etherdungeons.modding;

import com.etherdungeons.context.definitions.BeanDefinition;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class OrderConstraint {

    private final List<BeanDefinition<?>> beans = new ArrayList<>();
    private final List<OrderConstraint> prev = new ArrayList<>(), next = new ArrayList<>();

    public List<BeanDefinition<?>> getBeans() {
        return beans;
    }

    public List<OrderConstraint> getPrev() {
        return prev;
    }

    public List<OrderConstraint> getNext() {
        return next;
    }
}
