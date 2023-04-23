package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.util.CustomReader;
import com.epam.ld.module2.testing.util.CustomWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Mail server class.
 */
public class MailServer {

    CustomReader reader;
    CustomWriter writer;

    public MailServer(CustomReader reader, CustomWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    /**
     * Send notification.
     *
     * @param addresses  the addresses
     * @param messageContent the message content
     */
    public void send(String addresses, String messageContent, String outputFileName) {
        String message = addresses + ":" + messageContent;
        if (outputFileName == null || outputFileName.isBlank()){
            writer.writeToConsole(message);
        } else {
            try {
                writer.writeToFile(message, outputFileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String receive(String inputFileName) {
        String input;
        if (inputFileName == null || inputFileName.isBlank()){
            input = reader.readFromConsole();
        } else {
            input = readFromFile(inputFileName);
        }
        return input;
    }

    protected String readFromFile(String inputFileName) {
        List<String> lines;
        try (Stream<String> stream = Files.lines(Paths.get(inputFileName))) {
            lines = stream.collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines.get(0);
    }

}
