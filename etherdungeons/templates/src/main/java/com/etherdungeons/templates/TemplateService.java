package com.etherdungeons.templates;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Philipp
 */
public class TemplateService {
    private final Map<String, Template> templates;

    public TemplateService(Collection<TemplateProvider> providers) {
        templates = new HashMap<>();
        for (TemplateProvider provider : providers) {
            templates.putAll(provider.getTemplates());
        }
    }
    
    public Template get(String templateId) {
        return templates.get(templateId);
    }
}
