package com.etherdungeons.modding;

import com.etherdungeons.context.Context;
import com.etherdungeons.context.ContextBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class ModCombiner {
    private final List<SystemDefinition> systems = new ArrayList<>();

    public ModCombiner(Mod... mods) {
        this(Arrays.asList(mods));
    }

    public ModCombiner(Collection<Mod> mods) {
        for (Mod mod : mods) {
            systems.addAll(mod.getSystemDefinitions());
        }
    }
    
    
    public Context build() {
        Collections.sort(systems);
        ContextBuilder builder = new ContextBuilder();
        for (SystemDefinition system : systems) {
            builder.add(system.getBeanDefinition());
        }
        return builder.build();
    }
}
