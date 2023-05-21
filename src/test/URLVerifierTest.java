package test;

import main.exception.InvalidURIException;
import main.exception.InvalidURLException;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.concurrent.atomic.AtomicReference;

import static main.URLVerifier.getURLFromString;
import static org.junit.jupiter.api.Assertions.*;

class URLVerifierTest {

    private static final String VALID_URI_URL = "https://www.swisscom.ch";
    private static final String INVALID_URL = "www.swisscom.ch";
    private static final String INVALID_URI = "https://www. swisscom.ch";

    @Test
    void test_getURIFromString_validInput() {

        AtomicReference<URL> url = new AtomicReference<>();
        assertDoesNotThrow(() -> url.set(getURLFromString(VALID_URI_URL)));

        assertEquals(URL.class, url.get().getClass());

    }

    @Test
    void test_getURLFromString_invalidURL() {

        InvalidURLException exception = assertThrows(InvalidURLException.class, () -> getURLFromString(INVALID_URL));

        assertEquals("The provided String does not form a valid URL.", exception.getMessage());

    }

    @Test
    void test_getURLFromString_invalidURI() {

        InvalidURIException exception = assertThrows(InvalidURIException.class, () -> getURLFromString(INVALID_URI));

        assertEquals("The provided String does not form a valid URI.", exception.getMessage());

    }

}
