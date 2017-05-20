package com.etherdungeons.templates;

import com.etherdungeons.entitysystem.EntityComponent;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Philipp
 */
public class TemplateImporter {

    private final Gson gson = new Gson();
    private final Map<String, Constructor<?>> aliasToConstructor = new HashMap<>();

    public void register(Constructor<?> constructor) {
        aliasToConstructor.put(constructor.getDeclaringClass().getSimpleName(), constructor);
    }

    public void importTemplates(EntityData data, EntityId rootEntity, String... templates) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        for (String template : templates) {
            importTemplate(data, template, rootEntity);
        }
    }

    public void importTemplate(EntityData data, String template, EntityId... entities) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        JsonObject jsonObject = new JsonParser().parse(template).getAsJsonObject();
        Map<String, EntityId> entityMap = new HashMap<>();
        for (int i = 0; i < entities.length; i++) {
            entityMap.put("#" + i, entities[i]);
        }
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            if (!entityMap.containsKey(entry.getKey())) {
                entityMap.put(entry.getKey(), data.createEntity());
            }
        }
        importTemplate(data, entityMap, jsonObject);
    }
    
    public void importTemplate(EntityData data, Map<String, EntityId> entityMap, String template) throws InstantiationException, IllegalAccessException, UnsupportedOperationException, InvocationTargetException, IllegalArgumentException {
        importTemplate(data, entityMap, new JsonParser().parse(template).getAsJsonObject());
    }

    private void importTemplate(EntityData data, Map<String, EntityId> entityMap, JsonObject jsonObject) throws InstantiationException, IllegalAccessException, UnsupportedOperationException, InvocationTargetException, IllegalArgumentException {
        for (Map.Entry<String, JsonElement> entityEntry : jsonObject.entrySet()) {
            EntityId entity = entityMap.get(entityEntry.getKey());
            JsonObject jsonComponents = entityEntry.getValue().getAsJsonObject();
            for (Map.Entry<String, JsonElement> componentEntry : jsonComponents.entrySet()) {
                Constructor<?> constructor = aliasToConstructor.get(componentEntry.getKey());
                if(constructor == null) {
                    throw new UnsupportedOperationException("no alias for " + componentEntry.getKey());
                }
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                Object[] params = new Object[parameterTypes.length];
                JsonArray jsonParams = componentEntry.getValue().getAsJsonArray();
                if(jsonParams.size() != params.length) {
                    throw new IllegalArgumentException("parameter amount does not match");
                }
                for (int i = 0; i < params.length; i++) {
                    params[i] = parseParam(entityMap, parameterTypes[i], jsonParams.get(i));
                }
                data.set(entity, (EntityComponent) constructor.newInstance(params));
            }
        }
    }

    private Object parseParam(Map<String, EntityId> entityMap, Class<?> parameterType, JsonElement jsonParam) {
        if (jsonParam.isJsonPrimitive()) {
            JsonPrimitive jsonPrimitive = jsonParam.getAsJsonPrimitive();
            if (jsonPrimitive.isString()) {
                if (parameterType == EntityId.class) {
                    return entityMap.get(jsonPrimitive.getAsString());
                }
            }
        } else if(jsonParam.isJsonArray()) {
            JsonArray jsonArray = jsonParam.getAsJsonArray();
            Object array = Array.newInstance(parameterType.getComponentType(), jsonArray.size());
            for (int i = 0; i < jsonArray.size(); i++) {
                Array.set(array, i, parseParam(entityMap, parameterType.getComponentType(), jsonArray.get(i)));
            }
            return array;
        }
        return gson.fromJson(jsonParam, parameterType);
    }
}
