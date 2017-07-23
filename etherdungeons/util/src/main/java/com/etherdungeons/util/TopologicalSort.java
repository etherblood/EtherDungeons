package com.etherdungeons.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author Philipp
 */
public class TopologicalSort {

    public <T> List<T> sortedList(Collection<T> unsorted, Map<T, Collection<T>> dependencies) {
        return sortedList(unsorted, t -> dependencies.getOrDefault(t, Collections.emptyList()));
    }

    public <T> List<T> sortedList(Collection<T> unsorted, Function<T, Collection<T>> previous) {
        List<T> result = new ArrayList<>(unsorted.size());
        List<T> open = new ArrayList<>(unsorted);

        boolean repeat;
        do {
            repeat = false;
            Iterator<T> iterator = open.iterator();
            while (iterator.hasNext()) {
                T def = iterator.next();
                Collection<T> prev = previous.apply(def);
                if (result.containsAll(prev)) {
                    result.add(def);
                    iterator.remove();
                    repeat = true;
                }
            }
        } while (repeat);
        if (!open.isEmpty()) {
            throw new IllegalStateException("following items could not be sorted due to cycle: " + open.stream().map(Object::toString).collect(Collectors.joining(", ")));
        }
        return result;
    }
}
