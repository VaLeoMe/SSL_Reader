package main;

import main.exception.InvalidURIException;
import main.exception.InvalidURLException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class URLVerifier {

    private URLVerifier() {}

    public static URL getURLFromString(String urlString) {
        URL url;
        try {
            if (!urlString.startsWith("https://")) {
                throw new MalformedURLException();
            }
            url = new URI(urlString).toURL();
            return url;
        } catch (MalformedURLException e) {
            throw new InvalidURLException(e);
        } catch (URISyntaxException e) {
            throw new InvalidURIException(e);
        } catch (Exception e) {
            throw new RuntimeException("An unknown error occurred while verifying the URL", e);
        }
    }

}
