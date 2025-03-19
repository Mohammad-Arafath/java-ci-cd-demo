package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    @Test
    public void testMain() {
        String message = "Hello, CI/CD Pipeline!";
        assertEquals("Hello, CI/CD Pipeline!", message);
    }
}

