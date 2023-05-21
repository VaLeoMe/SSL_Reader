package main;

import java.net.URL;
import java.util.Scanner;

import static main.URLVerifier.getURLFromString;

public class InputHandler {

    private static Scanner in = new Scanner(System.in);

    private InputHandler() {}

    public static void setScanner(Scanner scanner) {
        in = scanner;
    }

    public static void printInputInstructions() {
        System.out.println("Welcome to the SSL certificate reader! :-)");
        System.out.println("Please enter a URL to retrieve its SSL certificate information:");
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

    private static String getNewUserInput() {
        return in.nextLine();
    }
}
