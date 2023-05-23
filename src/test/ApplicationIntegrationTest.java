package test;

import main.Application;
import main.InputHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationIntegrationTest {

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;
    private static final InputStream originalIn = System.in;

    @BeforeEach
    void setUpStreams() {
        outContent.reset();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void testMainSwisscom() {
        String testUrl = "www300.swisscom.com\nwww1.swisscom.com\ny\nwww1.swisscom.com\nn\n";
        InputStream in = new ByteArrayInputStream(testUrl.getBytes());
        System.setIn(in);
        InputHandler.setScanner(new Scanner(System.in));

        Application.main(new String[0]);

        assertTrue(outContent.toString().contains("Common Name:"));
        assertTrue(outContent.toString().contains("Issuer:"));
        assertTrue(outContent.toString().contains("Signature:"));
        assertTrue(outContent.toString().contains("Key Usage:"));
        assertTrue(outContent.toString().contains("Public Key:"));
    }
}
