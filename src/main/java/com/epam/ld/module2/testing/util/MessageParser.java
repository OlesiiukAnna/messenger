package com.epam.ld.module2.testing.util;

import com.epam.ld.module2.testing.template.Template;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageParser {

    private Template template;

    public static final String TAG_REGEX = "#\\{(\\w*)}";
    public static final String VALUE_REGEX = "#\\[(\\w*)]";

    public static final String TAG_NOT_FOUND = "Tag not found";
    public static final String VALUES_NOT_FOUND = "Values not found";

    public MessageParser(Template template) {
        this.template = template;
    }

    public String extractTag(String message) throws IllegalArgumentException {
        List<String> inputParameters = getTags(message);
        for (String inputParameter : inputParameters) {
            if (template.getTemplates().get(inputParameter) != null) {
                return inputParameter;
            }
        }
        throw new IllegalArgumentException(TAG_NOT_FOUND);
    }

    private List<String> getTags(String message) {
        return getParameters(Pattern.compile(TAG_REGEX).matcher(message));
    }

    public List<String> extractValues(String message) throws IllegalArgumentException {
        List<String> inputParameters = getValues(message);
        if (inputParameters.isEmpty()) {
            throw new IllegalArgumentException(VALUES_NOT_FOUND);
        }
        return inputParameters;
    }

    private List<String> getValues(String message) {
        return getParameters(Pattern.compile(VALUE_REGEX).matcher(message));
    }

    private static List<String> getParameters(Matcher matcher) {
        List<String> inputParameters = new ArrayList<>();
        while (matcher.find()) {
            inputParameters.add(matcher.group(1));
        }
        return inputParameters;
    }
}
