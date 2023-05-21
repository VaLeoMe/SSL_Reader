package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.concurrent.atomic.AtomicReference;

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
        printInputInstructions();
        assertTrue(outContent.toString().contains(
                "Welcome to the SSL certificate reader! :-)\n" +
                "Please enter a URL to retrieve its SSL certificate information:"));
    }

    @Test
    void test_getValidURLFromUser() {

        InputStream in = new ByteArrayInputStream(("invalid\nhttps://www.swisscom.ch\n").getBytes());
        System.setIn(in);

        AtomicReference<URL> url = new AtomicReference<>();
        assertDoesNotThrow(() -> url.set(getValidURLFromUser()));

        assertTrue(outContent.toString().contains("Please enter a new URL:"));
        assertEquals("https://www.swisscom.ch", url.get().toString());
    }

}
