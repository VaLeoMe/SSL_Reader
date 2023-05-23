package main;

import main.exception.InvalidURIException;
import main.exception.InvalidURLException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

public class InputHandler {

    private static Scanner in = new Scanner(System.in);

    private InputHandler() {}

    public static void setScanner(Scanner scanner) {
        in = scanner;
    }

    public static void printInputInstructions(boolean start) {
        if (start) {
            System.out.println("Welcome to the SSL certificate reader! :-)");
            System.out.println("Please enter a URL to retrieve its SSL certificate information:");
        } else {
            System.out.println("Seems that there could be no SSL certificates read...");
            System.out.println("Maybe the URL is invalid.");
            System.out.println("Please enter another URL:");
        }
    }

    public static URL getValidURLFromUser() {

        String userInput = getNewUserInput();
        URL url = null;

        while (url == null) {
            try {
                url = getURLFromString(userInput);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Please enter a new URL:");
                userInput = getNewUserInput();
            }
        }
        return url;
    }

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

    private static String getNewUserInput() {
        return in.nextLine();
    }
}
