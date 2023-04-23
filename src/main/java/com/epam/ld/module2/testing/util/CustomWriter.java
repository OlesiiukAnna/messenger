package com.epam.ld.module2.testing.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CustomWriter {

    public CustomWriter() {
    }

    public void writeToConsole(String message) {
        System.out.println(message);
    }

    public void writeToFile(String message, String outputFile) throws IOException {
        Files.write(Paths.get(outputFile), message.getBytes());
    }
}