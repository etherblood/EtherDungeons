package com.etherdungeons.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

/**
 *
 * @author Philipp
 */
public class CustomCollectors {

    public static <T> Collector<T, List<T>, T> singletonCollector() {
        return Collector.of(
                ArrayList::new,
                List::add,
                (left, right) -> {
                    left.addAll(right);
                    return left;
                },
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException();
                    }
                    return list.get(0);
                }
        );
    }
    
    public static <T> Collector<T, List<T>, T[]> arrayCollector(Class<T> type) {
        return Collector.of(
                ArrayList::new,
                List::add,
                (left, right) -> {
                    left.addAll(right);
                    return left;
                },
                list -> list.toArray((T[])Array.newInstance(type, list.size()))
        );
    }
}
