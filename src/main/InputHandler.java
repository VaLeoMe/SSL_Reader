package main;

import main.exception.InvalidURIException;
import main.exception.InvalidURLException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

public class InputHandler {

    private static Scanner in = new Scanner(System.in);
    private static final String HTTPS_PREFIX = "https://";

    private InputHandler() {}

    public static void setScanner(Scanner scanner) {
        in = scanner;
    }

    public static void printWelcomeLine() {
        System.out.println("Welcome to the SSL certificate reader! :-)");
    }

    public static void printInputInstructionsBasic() {
        System.out.println("Please enter a URL to retrieve its SSL certificate information:");
        System.out.print(HTTPS_PREFIX);
    }

    public static void printInputInstructionsWrongURL() {
        System.out.println("Seems that there could be no SSL certificates read...");
        System.out.println("Maybe the URL is invalid.");
        System.out.println("Please enter another URL:");
        System.out.print(HTTPS_PREFIX);
    }

    public static void printInputInstructionsEnd() {
        System.out.println("Would you like to enter another URL? (y/n)");
    }

    public static boolean checkIfUserWantsToContinue() {

        String userInput = getNewUserInput();

        while (!Objects.equals(userInput, "y") && !Objects.equals(userInput, "n")) {
            System.out.println("Please only enter 'y' or 'n'");
            userInput = getNewUserInput();
        }

        return Objects.equals(userInput, "y");
    }

    public static URL getURLFromUser() {

        String userInput = HTTPS_PREFIX;
        userInput += getNewUserInput();
        URL url = null;

        while (url == null) {
            try {
                url = stringToURL(userInput);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Please enter a new URL:");
                userInput = getNewUserInput();
            }
        }
        return url;
    }

    public static URL getNewURLFromUser() {
        printInputInstructionsWrongURL();
        return getURLFromUser();
    }

    public static URL stringToURL(String urlString) {
        URL url;
        try {
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
