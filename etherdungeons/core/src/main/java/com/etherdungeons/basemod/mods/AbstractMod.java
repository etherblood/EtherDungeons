package com.etherdungeons.basemod.mods;

import com.etherdungeons.context.definitions.BeanDefinitionFactory;
import com.etherdungeons.context.definitions.BeanDefinition;
import com.etherdungeons.modding.Mod;
import com.etherdungeons.modding.OrderConstraint;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Philipp
 */
public abstract class AbstractMod implements Mod {

    protected final List<BeanDefinition<?>> list = new ArrayList<>();
    protected final List<OrderConstraint> constraints = new ArrayList<>();

    protected static void defineOrder(OrderConstraint first, OrderConstraint second) {
//        first.getNext().add(second);
        second.getPrev().add(first);
    }

    protected BeanDefinition<?> add(Constructor<?> constructor, OrderConstraint... order) {
        BeanDefinition<?> beanDef = BeanDefinitionFactory.of(constructor);
        for (OrderConstraint orderConstraint : order) {
            orderConstraint.getBeans().add(beanDef);
        }
        list.add(beanDef);
        return beanDef;
    }

    @Override
    public Collection<BeanDefinition<?>> getBeanDefinitions() {
        return list;
    }

    @Override
    public Collection<OrderConstraint> getOrderConstraints() {
        return constraints;
    }
}
