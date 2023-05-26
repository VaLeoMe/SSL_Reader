package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLProtocolException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.cert.Certificate;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

import static main.SSLCertificateHelper.*;
import static org.junit.jupiter.api.Assertions.*;

class SSLCertificateHelperTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUpStreams() {

        outContent.reset();
        System.setOut(new PrintStream(outContent));

    }

    @Test
    void testGetSSLCertificatesValidURL() {

        AtomicReference<URL> url = new AtomicReference<>();
        AtomicReference<Certificate[]> certificates = new AtomicReference<>();

        assertDoesNotThrow(() -> url.set(new URL("https://www.swisscom.ch")));
        assertDoesNotThrow(() -> certificates.set(getSSLCertificates(url.get())));

        assertTrue(certificates.get().length > 0);

    }

    @Test
    void testGetSSLCertificatesInvalidURL() {

        AtomicReference<URL> url = new AtomicReference<>();

        assertDoesNotThrow(() -> url.set(new URL("https://www300.swisscom.ch")));
        assertThrows(RuntimeException.class, () -> getSSLCertificates(url.get()));

    }

    @Test
    void testGetSSLCertificatesInvalidURLTimeout() {

        AtomicReference<URL> url = new AtomicReference<>();

        assertDoesNotThrow(() -> url.set(new URL("https://www.swisscom.co")));
        Exception exception = assertThrows(RuntimeException.class, () -> getSSLCertificates(url.get()));

        assertTrue(exception.getCause() instanceof SocketTimeoutException || exception.getCause() instanceof SSLProtocolException);

    }
}
