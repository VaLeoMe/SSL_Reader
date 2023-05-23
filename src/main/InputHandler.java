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

    private InputHandler() {}

    public static void setScanner(Scanner scanner) {
        in = scanner;
    }

    public static void printInputInstructionsStart() {
        System.out.println("Welcome to the SSL certificate reader! :-)");
        System.out.println("Please enter a URL to retrieve its SSL certificate information:");
        System.out.print("https://");
    }

    public static void printInputInstructionsWrongURL() {
        System.out.println("Seems that there could be no SSL certificates read...");
        System.out.println("Maybe the URL is invalid.");
        System.out.println("Please enter another URL:");
        System.out.print("https://");
    }

    public static void printInputInstructionsEnd() {
        System.out.println("Would you like to enter another URL? (y/n)");
    }

    public static void printInputInstructionsContinue() {
        System.out.println("Please enter a URL to retrieve its SSL certificate information:");
        System.out.print("https://");
    }

    public static boolean checkIfUserWantsToContinue() {

        String userInput = getNewUserInput();

        while (!Objects.equals(userInput, "y") && !Objects.equals(userInput, "n")) {
            System.out.println("Please only enter a 'y' or a 'n'");
            userInput = getNewUserInput();
        }

        return Objects.equals(userInput, "y");
    }

    public static URL getValidURLFromUser() {

        String userInput = "https://";
        userInput += getNewUserInput();
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
