package com.epam.ld.module2.testing.template;

import java.util.List;

/**
 * The type Template engine.
 */
public class TemplateEngine {

    private static final String NOT_ENOUGH_VALUES_FOR_TEMPLATE = "Not enough values for template";
    private Template template;

    public TemplateEngine(Template template) {
        this.template = template;
    }

    public String generateMessage(String tag, List<String> values) {
        String actualTemplate = template.getTemplates().get(tag);
        String message;
        try {
            message = String.format(actualTemplate, values.toArray());
        } catch (RuntimeException exception) {
            throw new IllegalArgumentException(NOT_ENOUGH_VALUES_FOR_TEMPLATE);
        }
        return message;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }
}
