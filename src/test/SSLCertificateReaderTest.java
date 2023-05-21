package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.security.cert.Certificate;
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
    void test_readAndReturnSSLCertificates_validURL() {

        AtomicReference<URL> url = new AtomicReference<>();
        AtomicReference<Certificate[]> certificates = new AtomicReference<>();

        assertDoesNotThrow(() -> url.set(new URL("https://www.swisscom.ch")));
        assertDoesNotThrow(() -> certificates.set(readAndReturnSSLCertificates(url.get())));

        assertTrue(certificates.get().length > 0);

    }

    @Test
    void test_readAndReturnSSLCertificates_invalidURL() {

        AtomicReference<URL> url = new AtomicReference<>();
        AtomicReference<Certificate[]> certificates = new AtomicReference<>();

        assertDoesNotThrow(() -> url.set(new URL("https://www.notswisscom.ch")));
        assertDoesNotThrow(() -> certificates.set(readAndReturnSSLCertificates(url.get())));

        assertTrue(certificates.get().length == 0);

    }

    @Test
    void test_printInfoOfSSLCertificates() {

        Certificate[] certificates = new Certificate[10];

        assertThrows(NullPointerException.class, () -> printInfoOfSSLCertificates(certificates));
        assertTrue(outContent.toString().isEmpty());

    }
}
