/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherdungeons.context.dependencies;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Stream;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Philipp
 */
public class BeanDependencyFactoryTest {

    public BeanDependencyFactoryTest() {
    }

    @Test
    public void collectionDependency() throws NoSuchMethodException {
        Constructor constructor = TestBean.class.getConstructor(List.class);
        Type type = constructor.getGenericParameterTypes()[0];
        Class rawType = constructor.getParameterTypes()[0];
        BeanDependency result = BeanDependencyFactory.of(type, rawType);
        assertEquals(String.class, result.getDependencyType());
        assertTrue(result.paramFromCandidates(Stream.of("a", "b")) instanceof List);
    }

    @Test
    public void arrayDependency() throws NoSuchMethodException {
        Constructor constructor = TestBean.class.getConstructor(String[].class);
        Type type = constructor.getGenericParameterTypes()[0];
        Class rawType = constructor.getParameterTypes()[0];
        BeanDependency result = BeanDependencyFactory.of(type, rawType);
        assertEquals(String.class, result.getDependencyType());
        assertTrue(result.paramFromCandidates(Stream.of("a", "b")) instanceof String[]);
    }
}
