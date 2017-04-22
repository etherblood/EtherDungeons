package com.etherdungeons.context.dependencies;

import java.util.stream.Stream;

/**
 *
 * @author Philipp
 */
public interface BeanDependency {
    Class<?> getDependencyType();
    Object paramFromCandidates(Stream<?> candidates);
}
