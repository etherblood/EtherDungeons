package com.etherdungeons.templates.json;

import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.templates.Template;
import com.google.gson.JsonObject;

/**
 *
 * @author Philipp
 */
public class JsonTemplate implements Template {

    private final JsonTemplateImporter importer;
    private final JsonObject jsonObject;

    protected JsonTemplate(JsonTemplateImporter importer, JsonObject jsonObject) {
        this.importer = importer;
        this.jsonObject = jsonObject;
    }
    
    @Override
    public void applyTemplate(EntityData data, Object... args) {
        try {
            importer.applyTemplate(data, jsonObject, args);
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

}
