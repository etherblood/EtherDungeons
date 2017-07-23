package com.etherdungeons.modding;

import com.etherdungeons.context.Context;
import com.etherdungeons.context.ContextBuilder;
import com.etherdungeons.context.definitions.BeanDefinition;
import com.etherdungeons.util.TopologicalSort;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Philipp
 */
public class ModCombiner {

    private final List<BeanDefinition<?>> beans = new ArrayList<>();

    public ModCombiner(Mod... mods) {
        this(Arrays.asList(mods));
    }

    public ModCombiner(Collection<Mod> mods) {
        List<BeanDefinition<?>> open = new ArrayList<>();
        List<OrderConstraint> constraints = new ArrayList<>();
        for (Mod mod : mods) {
            open.addAll(mod.getBeanDefinitions());
            constraints.addAll(mod.getOrderConstraints());
        }
        int numBeans = open.size();
        Collection<OrderConstraint> normalizedConstraints = normalizedConstraints(constraints);
        List<OrderConstraint> orderedConstraints = new TopologicalSort().sortedList(normalizedConstraints, c -> c.getPrev());
        
        for (OrderConstraint constraint : orderedConstraints) {
            beans.addAll(constraint.getBeans());
            open.removeAll(constraint.getBeans());
        }
        beans.addAll(open);
        assert beans.size() == numBeans;
    }

    private Collection<OrderConstraint> normalizedConstraints(List<OrderConstraint> constraints) {
        List<Collection<BeanDefinition<?>>> groups = new ArrayList<>();
        Map<Collection<BeanDefinition<?>>, Collection<Collection<BeanDefinition<?>>>> parentMap = new HashMap<>();
        Map<Collection<BeanDefinition<?>>, Collection<Collection<BeanDefinition<?>>>> replacementMap = new HashMap<>();
        
        for (OrderConstraint constraint : constraints) {
            List<BeanDefinition<?>> group = constraint.getBeans();
            groups.add(group);
            for (OrderConstraint prevConstraint : constraint.getPrev()) {
                parentMap.computeIfAbsent(group, k -> new ArrayList<>()).add(prevConstraint.getBeans());
            }
            for (OrderConstraint nextConstraint : constraint.getNext()) {
                parentMap.computeIfAbsent(nextConstraint.getBeans(), k -> new ArrayList<>()).add(group);
            }
        }
        
        List<Collection<BeanDefinition<?>>> resultGroups = new ArrayList<>();
        Map<BeanDefinition<?>, Collection<BeanDefinition<?>>> itemToGroup = new HashMap<>();
        Deque<Collection<BeanDefinition<?>>> open = new ArrayDeque<>(groups);
        while(!open.isEmpty()) {
            Collection<BeanDefinition<?>> group = open.poll();
            resultGroups.add(group);
            for (BeanDefinition<?> item : group) {
                Collection<BeanDefinition<?>> previousGroup = itemToGroup.put(item, group);
                if(previousGroup != null) {
                    resultGroups.remove(group);
                    for (BeanDefinition<?> item2 : group) {
                        itemToGroup.remove(item2);
                    }
                    resultGroups.remove(previousGroup);
                    for (BeanDefinition<?> item2 : previousGroup) {
                        itemToGroup.remove(item2);
                    }
                    open.addAll(intersect(group, previousGroup, replacementMap));
                    break;
                }
            }
        }
        
        Map<Collection<BeanDefinition<?>>, OrderConstraint> groupToConstraint = new LinkedHashMap<>();
        for (Collection<BeanDefinition<?>> group : resultGroups) {
            OrderConstraint constraint = new OrderConstraint();
            constraint.getBeans().addAll(group);
            groupToConstraint.put(group, constraint);
        }
        for (Map.Entry<Collection<BeanDefinition<?>>, OrderConstraint> entry : groupToConstraint.entrySet()) {
            Collection<BeanDefinition<?>> group = entry.getKey();
            OrderConstraint constraint = entry.getValue();
            for (Collection<BeanDefinition<?>> parentGroup : parentMap.getOrDefault(group, Collections.emptyList())) {
                List<Collection<BeanDefinition<?>>> resolvedParents = resolve(parentGroup, replacementMap);
                for (Collection<BeanDefinition<?>> resolvedParent : resolvedParents) {
                    constraint.getPrev().add(groupToConstraint.get(resolvedParent));
                }
            }
        }
        return groupToConstraint.values();
    }
    
    private <T> List<T> resolve(T group, Map<T, Collection<T>> replacementMap) {
        List<T> result = new ArrayList<>();
        Deque<T> open = new ArrayDeque<>();
        open.add(group);
        while (!open.isEmpty()) {
            T c = open.poll();
            Collection<T> replacements = replacementMap.get(c);
            if(replacements == null) {
                result.add(c);
            } else {
                open.addAll(replacements);
            }
        }
        return result;
    }

    private <T> List<Collection<T>> intersect(Collection<T> a, Collection<T> b, Map<Collection<T>, Collection<Collection<T>>> replacementMap) {
        List<Collection<T>> result = new ArrayList<>();

        List<Collection<T>> replaceA = new ArrayList<>();
        replacementMap.put(a, replaceA);
        List<Collection<T>> replaceB = new ArrayList<>();
        replacementMap.put(b, replaceB);

        List<T> c = new ArrayList<>(a);
        c.removeAll(b);
        if (!c.isEmpty()) {
            result.add(c);
            replaceA.add(c);
        }

        List<T> d = new ArrayList<>(a);
        d.retainAll(b);
        assert !d.isEmpty();
        result.add(d);
        replaceA.add(d);
        replaceB.add(d);

        List<T> e = new ArrayList<>(b);
        e.removeAll(a);
        if (!e.isEmpty()) {
            result.add(e);
            replaceB.add(e);
        }
        return result;
    }

    public Context build() {
        ContextBuilder builder = new ContextBuilder();
        for (BeanDefinition<?> bean : beans) {
            builder.add(bean);
        }
        return builder.build();
    }
}
