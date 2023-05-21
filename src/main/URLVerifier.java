package main;

import main.exception.InvalidURIException;
import main.exception.InvalidURLException;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class URLVerifier {

    private URLVerifier() {}

    public static void verifyURL(String url) {
        try {
            new URL(url).toURI();
        } catch (MalformedURLException e) {
            throw new InvalidURLException(e);
        } catch (URISyntaxException e) {
            throw new InvalidURIException(e);
        } catch (Exception e) {
            throw new RuntimeException("An unknown error occurred while verifying the URL", e);
        }
    }

}
