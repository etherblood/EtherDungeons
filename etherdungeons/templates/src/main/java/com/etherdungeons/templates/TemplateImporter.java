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
import java.io.Reader;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Philipp
 */
public class TemplateImporter {

    private final Gson gson = new Gson();
    private final Map<String, Constructor<?>> aliasToConstructor = new HashMap<>();
    private final Map<String, JsonObject> templates = new HashMap<>();

    public void registerComponent(Constructor<?> constructor) {
        aliasToConstructor.put(constructor.getDeclaringClass().getSimpleName(), constructor);
    }

    public void readTemplate(Reader in) {
        JsonObject jsonFile = new JsonParser().parse(in).getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : jsonFile.entrySet()) {
            importTemplate(entry.getKey(), entry.getValue().getAsJsonObject());
        }
    }

    private void importTemplate(String name, JsonObject jsonTemplate) {
        if (templates.put(name, jsonTemplate) != null) {
            throw new IllegalStateException("template " + name + " was registered twice");
        }
    }

    private boolean bakeTemplate(JsonObject jsonTemplate) {
        JsonObject jsonTemplates = jsonTemplate.getAsJsonObject("templates");
        if (jsonTemplates != null) {
            JsonObject jsonComponents = jsonTemplate.getAsJsonObject("components");
            if (jsonComponents == null) {
                jsonComponents = new JsonObject();
                jsonTemplate.add("components", jsonComponents);
            }

            Iterator<Map.Entry<String, JsonElement>> it = jsonTemplates.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, JsonElement> entry = it.next();
                JsonObject childTemplate = templates.get(entry.getKey());
                if (childTemplate == null || !bakeTemplate(childTemplate)) {
                    return false;
                }
                JsonArray jsonParams = entry.getValue().getAsJsonArray();
                Map<String, JsonElement> replaceMap = new HashMap<>();
                for (int i = 0; i < jsonParams.size(); i++) {
                    replaceMap.put("#" + i, jsonParams.get(i));
                }
                JsonObject sourceComponents = childTemplate.getAsJsonObject("components");
                if (sourceComponents != null) {
                    mergeChilds(sourceComponents, jsonComponents, replaceMap);
                }
                it.remove();
            }
            jsonTemplate.remove("templates");
        }
        return true;
    }

    private void mergeChilds(JsonObject source, JsonObject dest, Map<String, JsonElement> replaceMap) {
        for (Map.Entry<String, JsonElement> entry : source.entrySet()) {
            JsonObject sourceEntity = entry.getValue().getAsJsonObject();
            String key = entry.getKey();
            if (replaceMap.containsKey(key)) {
                key = replaceMap.get(key).getAsString();
            }
            JsonObject destEntity = dest.getAsJsonObject(key);
            if (destEntity == null) {
                dest.add(key, clone(sourceEntity, replaceMap));
            } else {
                for (Map.Entry<String, JsonElement> entry1 : sourceEntity.entrySet()) {
                    destEntity.add(entry1.getKey(), clone(entry1.getValue(), replaceMap));
                }
            }
        }
    }

    private JsonElement clone(JsonElement source, Map<String, JsonElement> replaceMap) {
        if (source.isJsonObject()) {
            return clone(source.getAsJsonObject(), replaceMap);
        }
        if (source.isJsonPrimitive()) {
            return clone(source.getAsJsonPrimitive(), replaceMap);
        }
        if (source.isJsonArray()) {
            return clone(source.getAsJsonArray(), replaceMap);
        }
        if (source.isJsonNull()) {
            return source;
        }
        throw new UnsupportedOperationException();
    }

    private JsonObject clone(JsonObject source, Map<String, JsonElement> replaceMap) {
        JsonObject result = new JsonObject();
        for (Map.Entry<String, JsonElement> entry : source.entrySet()) {
            result.add(entry.getKey(), clone(entry.getValue(), replaceMap));
        }
        return result;
    }

    private JsonArray clone(JsonArray source, Map<String, JsonElement> replaceMap) {
        JsonArray result = new JsonArray();
        for (JsonElement jsonElement : source) {
            result.add(clone(jsonElement, replaceMap));
        }
        return result;
    }

    private JsonElement clone(JsonPrimitive source, Map<String, JsonElement> replaceMap) {
        if (source.isString()) {
            String value = source.getAsString();
            if (replaceMap.containsKey(value)) {
                return clone(replaceMap.get(value), Collections.emptyMap());
            }
        }
        return source;
    }

    public void applyTemplate(EntityData data, String name, Object... params) throws ReflectiveOperationException {
        JsonObject jsonTemplate = templates.get(name);
        if (!bakeTemplate(jsonTemplate)) {
            throw new IllegalArgumentException("template " + name + " not defined");
        }
        applyTemplate(data, jsonTemplate, params);
    }

    private void applyTemplate(EntityData data, JsonObject jsonTemplate, Object... params) throws ReflectiveOperationException {
        Map<String, Object> paramsMap = new HashMap<>();
        for (int i = 0; i < params.length; i++) {
            paramsMap.put("#" + i, params[i]);
        }
        applyTemplate(data, jsonTemplate, paramsMap);
    }

    private void applyTemplate(EntityData data, JsonObject jsonTemplate, Map<String, Object> paramsMap) throws ReflectiveOperationException {
        JsonObject jsonComponents = jsonTemplate.getAsJsonObject("components");
        if (jsonComponents != null) {
            applyComponents(data, jsonComponents, paramsMap);
        }
    }

    private void applyComponents(EntityData data, JsonObject jsonObject, Map<String, Object> paramsMap) throws ReflectiveOperationException {
        for (Map.Entry<String, JsonElement> entityEntry : jsonObject.entrySet()) {
            EntityId entity = (EntityId) paramsMap.get(entityEntry.getKey());
            JsonObject jsonComponents = entityEntry.getValue().getAsJsonObject();
            for (Map.Entry<String, JsonElement> componentEntry : jsonComponents.entrySet()) {
                Constructor<?> constructor = aliasToConstructor.get(componentEntry.getKey());
                if (constructor == null) {
                    throw new UnsupportedOperationException("no alias for " + componentEntry.getKey());
                }
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                Object[] params = new Object[parameterTypes.length];
                JsonArray jsonParams = componentEntry.getValue().getAsJsonArray();
                if (jsonParams.size() != params.length) {
                    throw new IllegalArgumentException("parameter amount does not match");
                }
                for (int i = 0; i < params.length; i++) {
                    params[i] = parseParam(paramsMap, parameterTypes[i], jsonParams.get(i));
                }
                data.set(entity, (EntityComponent) constructor.newInstance(params));
            }
        }
    }

    private Object parseParam(Map<String, Object> paramsMap, Class<?> parameterType, JsonElement jsonParam) {
        if (jsonParam.isJsonPrimitive()) {
            JsonPrimitive jsonPrimitive = jsonParam.getAsJsonPrimitive();
            if (jsonPrimitive.isString()) {
                String stringValue = jsonPrimitive.getAsString();
                if (paramsMap.containsKey(stringValue)) {
                    return paramsMap.get(stringValue);
                }
            }
        } else if (jsonParam.isJsonArray()) {
            JsonArray jsonArray = jsonParam.getAsJsonArray();
            Object array = Array.newInstance(parameterType.getComponentType(), jsonArray.size());
            for (int i = 0; i < jsonArray.size(); i++) {
                Array.set(array, i, parseParam(paramsMap, parameterType.getComponentType(), jsonArray.get(i)));
            }
            return array;
        }
        return gson.fromJson(jsonParam, parameterType);
    }
}
