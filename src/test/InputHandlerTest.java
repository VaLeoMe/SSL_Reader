package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static main.InputHandler.*;
import static org.junit.jupiter.api.Assertions.*;

class InputHandlerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final InputStream actualIn = System.in;
    private static final PrintStream actualOut = System.out;

    private static final String TEST_INPUT = "Some input String.";

    @BeforeEach
    void setUpStreams() {

        outContent.reset();
        System.setOut(new PrintStream(outContent));

        InputStream in = new ByteArrayInputStream(TEST_INPUT.getBytes());
        System.setIn(in);
    }

    @AfterEach
    void restoreStreams() {
        System.setIn(actualIn);
        System.setOut(actualOut);
    }

    @Test
    void testLogInputInstructions() {
        logInputInstructions();
        assertTrue(outContent.toString().contains(
                "Welcome to the SSL certificate reader! :-)\n" +
                "Please enter a URL to retrieve its SSL certificate information:"));
    }

    @Test
    void test_GetAndReturnUserInput() {
        String result = getAndReturnUserInput();
        assertEquals(TEST_INPUT, result);
    }

    @Test
    void test_verifyUserInput_validInput() {
        assertDoesNotThrow(() -> verifyUserInput("https://www.swisscom.ch"));
        assertTrue(outContent.toString().isEmpty());
    }

    @Test
    void test_verifyUserInput_invalidInput() {
        assertDoesNotThrow(() -> verifyUserInput("invalidInput"));
        assertTrue(outContent.toString().length() > 0);
    }

}
