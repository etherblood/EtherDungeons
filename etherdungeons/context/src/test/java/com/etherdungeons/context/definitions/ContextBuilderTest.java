package com.etherdungeons.context.definitions;

import com.etherdungeons.context.Context;
import com.etherdungeons.context.ContextBuilder;
import com.etherdungeons.context.dependencies.BeanDependencyFactory;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Philipp
 */
public class ContextBuilderTest {

    @Test
    public void outputIsInInputOrder() {
        ContextBuilder builder = new ContextBuilder();
        builder.add(BeanDefinitionFactory.of(String.class, (params) -> "a", Integer.class, Short.class));
        builder.add(BeanDefinitionFactory.of(Integer.class, (params) -> 1, Short.class));
        builder.add(BeanDefinitionFactory.of(String.class, (params) -> "b"));
        builder.add(BeanDefinitionFactory.of(Short.class, (params) -> (short) 2));

        Context context = builder.build();
        List<Object> beans = context.getBeans(Object.class);
        assertEquals("a", beans.get(0));
        assertEquals(1, beans.get(1));
        assertEquals("b", beans.get(2));
        assertEquals((short) 2, beans.get(3));
    }

    @Test
    public void listIsInInputOrder() {
        ContextBuilder builder = new ContextBuilder();
        builder.add(BeanDefinitionFactory.of(String.class, (params) -> "a"));
        builder.add(BeanDefinitionFactory.of(String.class, (params) -> "b"));
        builder.add(BeanDefinitionFactory.of(String.class, (params) -> "c"));
        builder.add(BeanDefinitionFactory.of_(Set.class,
                (Object[] params) -> new HashSet<>((List) params[0]),
                BeanDependencyFactory.of(String.class, Collectors.toList())));

        Context context = builder.build();
        Set<Object> set = context.getBean(Set.class);
        assertTrue(set.containsAll(Arrays.asList("a", "b", "c")));
        assertEquals(3, set.size());
    }

    @Test
    public void injectSubclassOfDependency() {
        ContextBuilder builder = new ContextBuilder();
        builder.add(BeanDefinitionFactory.of(HashSet.class, (params) -> new HashSet<>()));
        builder.add(BeanDefinitionFactory.of(Boolean.class, (params) -> params[0] instanceof HashSet, Set.class));

        Context context = builder.build();
        List<Object> beans = context.getBeans(Object.class);
        assertTrue((Boolean) beans.get(1));
    }

    @Test(expected = IllegalStateException.class)
    public void missingDependency() {
        ContextBuilder builder = new ContextBuilder();
        builder.add(BeanDefinitionFactory.of(Integer.class, (params) -> 0, Short.class));
        builder.build();
    }

    @Test(expected = IllegalStateException.class)
    public void cyclicDependency() {
        ContextBuilder builder = new ContextBuilder();
        builder.add(BeanDefinitionFactory.of(Integer.class, (params) -> 0, Short.class));
        builder.add(BeanDefinitionFactory.of(Short.class, (params) -> (short) 0, Integer.class));
        builder.build();
    }

}
