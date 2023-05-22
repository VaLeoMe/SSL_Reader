package test;

import main.Application;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

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
    void testMain_Swisscom() {
        String testUrl = "https://www.swisscom.ch\n";
        InputStream in = new ByteArrayInputStream(testUrl.getBytes());
        System.setIn(in);

        Application.main(new String[0]);

        assertTrue(outContent.toString().contains("Common Name:"));
        assertTrue(outContent.toString().contains("Issuer:"));
        assertTrue(outContent.toString().contains("Signature:"));
        assertTrue(outContent.toString().contains("Key Usage:"));
        assertTrue(outContent.toString().contains("Public Key:"));
    }

    //@Test
    //void testMain_Youtube() {
    //    String testUrl = "https://youtube.com\n";
    //    InputStream in = new ByteArrayInputStream(testUrl.getBytes());
    //    System.setIn(in);
//
    //    Application.main(new String[0]);
//
    //    assertTrue(outContent.toString().contains("Public Key:"));
    //}
}
