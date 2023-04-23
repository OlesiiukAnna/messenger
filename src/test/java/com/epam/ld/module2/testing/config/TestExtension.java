package com.epam.ld.module2.testing.config;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TestExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    @Override
    public void beforeTestExecution(ExtensionContext extensionContext){
        System.out.println("before test");
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) {
        System.out.println("after test");
    }
}
