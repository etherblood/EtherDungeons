package com.etherdungeons.basemod.mods;

import com.etherdungeons.context.definitions.BeanDefinition;
import com.etherdungeons.context.definitions.BeanDefinitionFactory;
import com.etherdungeons.modding.Mod;
import com.etherdungeons.modding.OrderConstraint;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.slf4j.impl.SimpleLoggerFactory;

/**
 *
 * @author Philipp
 */
public class SimpleLoggerMod implements Mod {

    @Override
    public Collection<BeanDefinition<?>> getBeanDefinitions() {
        try {
            return Arrays.asList(BeanDefinitionFactory.of(SimpleLoggerFactory.class.getConstructor()));
        } catch (NoSuchMethodException | SecurityException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Collection<OrderConstraint> getOrderConstraints() {
        return Collections.emptyList();
    }

}
