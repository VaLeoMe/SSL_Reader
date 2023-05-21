package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static main.InputHandler.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InputHandlerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final InputStream originalIn = System.in;

    private static final String TEST_INPUT = "Some input String.";

    @BeforeEach
    void setUpStreams() {

        System.setOut(new PrintStream(outContent));

        InputStream in = new ByteArrayInputStream(TEST_INPUT.getBytes());
        System.setIn(in);
    }

    @AfterEach
    void restoreStreams() {
        System.setIn(originalIn);
    }

    @Test
    void testLogInputInstructions() {
        logInputInstructions();
        assertTrue(outContent.toString().contains(
                "Welcome to the SSL certificate reader! :-)\n" +
                "Please enter a URL to retrieve its SSL certificate information:"));
    }

    @Test
    void testGetAndReturnUserInput() {
        String result = getAndReturnUserInput();
        assertEquals(TEST_INPUT, result);
    }

}
