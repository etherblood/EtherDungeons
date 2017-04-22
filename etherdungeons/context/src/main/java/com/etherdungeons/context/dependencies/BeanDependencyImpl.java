package com.etherdungeons.context.dependencies;

import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 *
 * @author Philipp
 */
class BeanDependencyImpl implements BeanDependency {

    private final Class<?> dependencyType;
    private final Collector collector;

    public BeanDependencyImpl(Class<?> type, Collector collector) {
        this.dependencyType = type;
        this.collector = collector;
    }

    @Override
    public Class<?> getDependencyType() {
        return dependencyType;
    }

    @Override
    public Object paramFromCandidates(Stream<?> candidates) {
        return candidates.collect(collector);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{dependencyType=" + dependencyType + ", collector=" + collector + '}';
    }
}
