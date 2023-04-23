package com.epam.ld.module2.testing.util;

import com.epam.ld.module2.testing.template.Template;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageParserTest {

    public static final String MESSAGE_WITH_TAG_AND_VALUES =
            "message with value #[value_1] #{tag} and #[value_2]";
    public static final String MESSAGE_WITH_TAG_ONLY = "message with tag #{tag}";
    public static final String MESSAGE_WITH_VALUE_ONLY = "message with values #[value_1]";

    public static final String TEMPLATE_VALUE_1 = "template_message with values %1$s %2$s";
    public static final String TAG = "tag";
    public static final String VALUE_1 = "value_1";
    public static final String VALUE_2 = "value_2";

    public static final String TAG_NOT_FOUND_EXCEPTION_MESSAGE = "Tag not found";
    public static final String VALUES_NOT_FOUND_EXCEPTION_MESSAGE = "Values not found";

    @Mock
    private Template templateMock;

    @InjectMocks
    private MessageParser testedInstance;

    @BeforeEach
    public void setUp() {
        testedInstance = new MessageParser(templateMock);
    }

    @Test
    void testExtractTag() {
        Map<String, String> templates = Map.of(TAG, TEMPLATE_VALUE_1);
        when(templateMock.getTemplates()).thenReturn(templates);

        String result = testedInstance.extractTag(MESSAGE_WITH_TAG_AND_VALUES);

        assertEquals(TAG, result);
    }

    @Test
    void testExtractTagThrowsException() {
        Exception exception = assertThrows(RuntimeException.class, () ->
                testedInstance.extractTag(MESSAGE_WITH_VALUE_ONLY));

        String actualExceptionMessage = exception.getMessage();

        assertTrue(actualExceptionMessage.contains(TAG_NOT_FOUND_EXCEPTION_MESSAGE));
    }

    @Test
    void testExtractValues() {
        List<String> result = testedInstance.extractValues(MESSAGE_WITH_TAG_AND_VALUES);

        assertEquals(List.of(VALUE_1, VALUE_2), result);
    }

    @Test
    void testExtractValuesThrowsException() {
        Exception exception = assertThrows(RuntimeException.class, () ->
                testedInstance.extractValues(MESSAGE_WITH_TAG_ONLY));

        String actualExceptionMessage = exception.getMessage();

        assertTrue(actualExceptionMessage.contains(VALUES_NOT_FOUND_EXCEPTION_MESSAGE));
    }

}