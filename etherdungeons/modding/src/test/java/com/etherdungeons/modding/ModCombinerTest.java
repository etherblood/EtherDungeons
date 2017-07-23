package com.etherdungeons.modding;

import com.etherdungeons.context.Context;
import com.etherdungeons.context.definitions.BeanDefinition;
import com.etherdungeons.context.definitions.BeanDefinitionFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Philipp
 */
public class ModCombinerTest {

    public ModCombinerTest() {
    }

    @Test
    public void testBuild() {
        final BeanDefinition<ArrayList> intDef = BeanDefinitionFactory.of(ArrayList.class);
        final BeanDefinition<HashSet> stringDef = BeanDefinitionFactory.of(HashSet.class);
        final BeanDefinition<HashMap> longDef = BeanDefinitionFactory.of(HashMap.class);
        final BeanDefinition<LinkedList> byteDef = BeanDefinitionFactory.of(LinkedList.class);
        ModCombiner modCombiner = new ModCombiner(new Mod() {
            @Override
            public Collection<BeanDefinition<?>> getBeanDefinitions() {
                return Arrays.asList(intDef, stringDef, longDef, byteDef);
            }

            @Override
            public Collection<OrderConstraint> getOrderConstraints() {
                OrderConstraint byteOrder = new OrderConstraint();
                byteOrder.getBeans().add(byteDef);
                
                OrderConstraint intLongOrder = new OrderConstraint();
                intLongOrder.getBeans().add(longDef);
                intLongOrder.getBeans().add(intDef);
                
                OrderConstraint stringOrder = new OrderConstraint();
                stringOrder.getBeans().add(stringDef);
                
                byteOrder.getNext().add(intLongOrder);
                stringOrder.getPrev().add(intLongOrder);
                return Arrays.asList(stringOrder, byteOrder, intLongOrder);
            }
        });
        Context context = modCombiner.build();
        List<Object> beans = context.getBeans(Object.class);
        assertTrue(beans.get(0) instanceof LinkedList);
//        assertTrue(beans.get(1) instanceof ArrayList);
//        assertTrue(beans.get(2) instanceof HashMap);
        assertTrue(beans.get(3) instanceof HashSet);
    }

}