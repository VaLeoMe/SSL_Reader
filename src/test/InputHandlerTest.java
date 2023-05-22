package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

import static main.InputHandler.*;
import static org.junit.jupiter.api.Assertions.*;

class InputHandlerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream actualOut = System.out;

    private static final String TEST_INPUT = "Some input String.";

    @BeforeEach
    void setUpStreams() {
        outContent.reset();
        System.setOut(new PrintStream(outContent));
        ByteArrayInputStream in = new ByteArrayInputStream(TEST_INPUT.getBytes());
        setScanner(new Scanner(in));
    }

    @AfterEach
    void restoreStreams() {
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

        ByteArrayInputStream in = new ByteArrayInputStream((
                "invalid\nstill invalid\nhttps://www.swisscom.ch").getBytes());
        setScanner(new Scanner(in));

        AtomicReference<URL> url = new AtomicReference<>();
        assertDoesNotThrow(() -> url.set(getValidURLFromUser()));

        assertTrue(outContent.toString().contains("Please enter a new URL:"));
        assertEquals("https://www.swisscom.ch", url.get().toString());
    }

}