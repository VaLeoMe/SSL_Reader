package test;

import org.junit.jupiter.api.Test;

import static main.URLVerifier.verifyURL;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class URLVerifierTest {

    private static final String validURL = "https://www.swisscom.ch";
    private static final String invalidURL = "https://www.notswisscom.ch";

    @Test
    void testVerifyURL_validURL() {
        assertDoesNotThrow(() -> verifyURL(validURL));
    }

    @Test
    void testVerifyURL_invalidURL() {
        assertThrows(Exception.class, () -> verifyURL(invalidURL));
    }

}
