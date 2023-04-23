package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.config.TestExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TemplateEngineTest {

    public static final String EXPECTED_MESSAGE_1 = "template_message with values value_1 value_2";
    public static final String EXPECTED_MESSAGE_2 = "template_message with values value_3";
    public static final String TEMPLATE_TAG_1 = "template_tag_1";
    public static final String TEMPLATE_VALUE_1 = "template_message with values %1$s %2$s";
    public static final String TEMPLATE_TAG_2 = "template_tag_2";
    public static final String TEMPLATE_VALUE_2 = "template_message with values %1$s";

    public static final String VALUE_1 = "value_1";
    public static final String VALUE_2 = "value_2";
    public static final String VALUE_3 = "value_3";

    private static final String NOT_ENOUGH_VALUES_FOR_TEMPLATE = "Not enough values for template";

    @Mock
    private Template templateMock;

    @InjectMocks
    private TemplateEngine testedInstance;

    @BeforeEach
    public void setUp() {
        testedInstance = new TemplateEngine(templateMock);
        Map<String, String> templates = Map.of(TEMPLATE_TAG_1, TEMPLATE_VALUE_1, TEMPLATE_TAG_2, TEMPLATE_VALUE_2);
        when(templateMock.getTemplates()).thenReturn(templates);
    }

    @ParameterizedTest
    @MethodSource("templatesProvider")
    public void testGenerateMessage(List<String> values, String expectedMessage, String tag) {

        String result = testedInstance.generateMessage(tag, values);

        assertEquals(expectedMessage, result);
    }

    private static Stream<Arguments> templatesProvider() {
        return Stream.of(
                Arguments.of(List.of(VALUE_1, VALUE_2), EXPECTED_MESSAGE_1, TEMPLATE_TAG_1),
                Arguments.of(List.of(VALUE_3), EXPECTED_MESSAGE_2, TEMPLATE_TAG_2)
        );
    }

    @Test
    @ExtendWith(TestExtension.class)
    public void testGenerateMessageThrowsExceptionWhenNotEnoughValues() {
        Exception exception = assertThrows(RuntimeException.class, () ->
                testedInstance.generateMessage(TEMPLATE_TAG_1, List.of(VALUE_1)));
                String actualExceptionMessage = exception.getMessage();

        assertTrue(actualExceptionMessage.contains(NOT_ENOUGH_VALUES_FOR_TEMPLATE));
    }

}
