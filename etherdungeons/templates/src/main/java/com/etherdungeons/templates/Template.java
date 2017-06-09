package com.etherdungeons.templates;

import com.etherdungeons.entitysystem.EntityData;

/**
 *
 * @author Philipp
 */
public interface Template {
    void applyTemplate(EntityData data, Object... args);
}
