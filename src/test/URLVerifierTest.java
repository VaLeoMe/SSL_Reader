package test;

import main.exception.InvalidURIException;
import main.exception.InvalidURLException;
import org.junit.jupiter.api.Test;

import static main.URLVerifier.verifyURL;
import static org.junit.jupiter.api.Assertions.*;

class URLVerifierTest {

    private static final String VALID_URL = "https://www.swisscom.ch";
    private static final String INVALID_URL = "invalidURL";
    private static final String INVALID_URI = "https://www. swisscom.ch";

    @Test
    void test_VerifyURL_validURL() {
        assertDoesNotThrow(() -> verifyURL(VALID_URL));
    }

    @Test
    void test_VerifyURL_invalidURL() {

        InvalidURLException exception = assertThrows(InvalidURLException.class, () -> verifyURL(INVALID_URL));

        assertEquals("The provided URL is not valid", exception.getMessage());

    }

    @Test
    void test_VerifyURL_invalidURI() {

        InvalidURIException exception = assertThrows(InvalidURIException.class, () -> verifyURL(INVALID_URI));

        assertEquals("The provided URL does not form a valid URI", exception.getMessage());

    }

}
