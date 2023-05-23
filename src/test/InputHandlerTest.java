package test;

import main.InputHandler;
import main.exception.InvalidURIException;
import main.exception.InvalidURLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URL;
import java.util.Scanner;

import static main.InputHandler.*;
import static org.junit.jupiter.api.Assertions.*;

class InputHandlerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private static final String VALID_INPUT = "www.swisscom.ch\n";
    private static final String EXPECTED_URL = "https://www.swisscom.ch";
    private static final InputStream ORIGINAL_IN = System.in;

    @BeforeEach
    void setUpStreams() {
        outContent.reset();
        System.setOut(new PrintStream(outContent));

        InputStream in = new ByteArrayInputStream(VALID_INPUT.getBytes());
        System.setIn(in);
        setScanner(new Scanner(System.in));
    }

    @AfterEach
    void restoreStreams() {
        System.setIn(ORIGINAL_IN);
    }

    @Test
    void testLogInputInstructionsStart() {
        printWelcomeLine();
        assertTrue(outContent.toString().contains("Welcome to the SSL certificate reader! :-)\n"));
    }

    @Test
    void testLogInputInstructionsBasic() {
        printInputInstructionsBasic();
        assertTrue(outContent.toString().contains("""
                        Please enter a URL to retrieve its SSL certificate information:
                        https://"""));
    }

    @Test
    void testLogInputInstructionsWrongURL() {
        printInputInstructionsWrongURL();
        assertTrue(outContent.toString().contains("""
                        Seems that there could be no SSL certificates read...
                        Maybe the URL is invalid.
                        Please enter another URL:
                        https://"""));
    }

    @Test
    void testLogInputInstructionsEnd() {
        printInputInstructionsEnd();
        assertTrue(outContent.toString().contains("Would you like to enter another URL? (y/n)\n"));
    }

    @Test
    void testCheckIfUserWantsToContinue() {
        String continueString = "notYorN\ny\n";
        InputStream in = new ByteArrayInputStream(continueString.getBytes());
        System.setIn(in);
        InputHandler.setScanner(new Scanner(System.in));

        assertTrue(checkIfUserWantsToContinue());
        assertTrue(outContent.toString().contains("Please only enter 'y' or 'n'"));

    }

    @Test
    void testGetValidURLFromUser() {
        URL url = getURLFromUser();
        assertNotNull(url);
        assertEquals(EXPECTED_URL, url.toString());
    }

    @Test
    void testGetURLFromStringValidURL() {
        URL url = stringToURL(EXPECTED_URL);
        assertNotNull(url);
        assertEquals(EXPECTED_URL, url.toString());
    }

    @Test
    void testGetURLFromStringInvalidURL() {
        assertThrows(InvalidURLException.class, () ->
            stringToURL("htt://www.swisscom.ch"));
    }

    @Test
    void testGetURLFromStringInvalidURI() {
        assertThrows(InvalidURIException.class, () ->
            stringToURL("https://www.swisscom.ch/abc|def"));
    }

    @Test
    void testGetURLFromStringUnknownError() {
        assertThrows(RuntimeException.class, () ->
            stringToURL(null));
    }

}