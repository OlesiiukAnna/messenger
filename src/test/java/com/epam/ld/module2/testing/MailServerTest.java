package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.util.CustomReader;
import com.epam.ld.module2.testing.util.CustomWriter;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MailServerTest {

    private static final String INCOME_MESSAGE = "incomeMessage";
    private static final String BLANK_FILE_NAME = "";
    private static final String FILE_NAME = "fileName";

    private static final String ADDRESS = "address";
    private static final String MESSAGE_CONTENT = "messageContent";
    private static final String MESSAGE = "address:messageContent";

    @Mock
    private CustomReader readerMock;
    @Mock
    private CustomWriter writerMock;

    @Spy
    @InjectMocks
    private MailServer testedInstance = new MailServer(readerMock, writerMock);

    @Test
    @Tag("console")
    void testSendToConsole() throws IOException {
        testedInstance.send(ADDRESS, MESSAGE_CONTENT, BLANK_FILE_NAME);

        verify(writerMock, times(1)).writeToConsole(MESSAGE);
        verify(writerMock, never()).writeToFile(MESSAGE, FILE_NAME);
    }

    @Test
    @Tag("file")
    void testSendToFile() throws IOException {
        testedInstance.send(ADDRESS, MESSAGE_CONTENT, FILE_NAME);

        verify(writerMock, times(1)).writeToFile(MESSAGE, FILE_NAME);
        verify(writerMock, never()).writeToConsole(MESSAGE);
    }

    @Test
    @Tag("console")
    void testReceiveInputFromConsole() {
        when(readerMock.readFromConsole()).thenReturn(INCOME_MESSAGE);

        String input = testedInstance.receive(BLANK_FILE_NAME);

        assertSame(input, INCOME_MESSAGE);
        verify(readerMock, times(1)).readFromConsole();
        verify(testedInstance, never()).readFromFile(BLANK_FILE_NAME);
    }

    @Test
    @Tag("file")
    void testReceiveInputFromFile() {
        doReturn(INCOME_MESSAGE).when(testedInstance).readFromFile(FILE_NAME);

        String input = testedInstance.receive(FILE_NAME);

        assertSame(input, INCOME_MESSAGE);
        verify(readerMock, never()).readFromConsole();
        verify(testedInstance, times(1)).readFromFile(FILE_NAME);
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "USERNAME", matches = "Example")
    protected void testReadFromFile(@TempDir Path tempDir) throws IOException {
        Path message = tempDir.resolve("message.txt");

        List<String> lines = List.of(INCOME_MESSAGE);
        Files.write(message, lines);

        assertTrue(Files.exists(message), "File should exist");
        assertLinesMatch(lines, Files.readAllLines(message));
    }

}
