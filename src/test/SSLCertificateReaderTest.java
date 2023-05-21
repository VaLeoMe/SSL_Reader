package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static main.SSLCertificateReader.*;
import static org.junit.jupiter.api.Assertions.*;

class SSLCertificateReaderTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUpStreams() {

        outContent.reset();
        System.setOut(new PrintStream(outContent));

    }

    @Test
    void test_readAndReturnSSLCertificates() {

        AtomicReference<URL> url = new AtomicReference<>();
        AtomicReference<List<Certificate>> certificates = new AtomicReference<>();

        assertDoesNotThrow(() -> url.set(new URL("https://www.swisscom.ch")));
        assertDoesNotThrow(() -> certificates.set(readAndReturnSSLCertificates(url.get())));

        assertFalse(certificates.get().isEmpty());

    }

    @Test
    void test_printInfoOfSSLCertificates() {

        List<Certificate> certificates = new ArrayList<>();

        assertDoesNotThrow(() -> printInfoOfSSLCertificates(certificates));
        assertTrue(outContent.toString().length() > 0);

    }
}
