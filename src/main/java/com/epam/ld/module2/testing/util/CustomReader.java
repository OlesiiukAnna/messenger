package com.epam.ld.module2.testing.util;

import java.util.Scanner;

public class CustomReader {

    private Scanner scanner;

    public CustomReader() {
        this.scanner = new Scanner(System.in);
    }

    public String readFromConsole() {
        return scanner.nextLine();
    }

}