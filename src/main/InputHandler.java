package main;

import java.net.URL;
import java.util.Scanner;

import static main.URLVerifier.getURLFromString;

public class InputHandler {

    private static final Scanner in = new Scanner(System.in);

    private InputHandler() {}

    public static void printInputInstructions() {
        System.out.println("Welcome to the SSL certificate reader! :-)");
        System.out.println("Please enter a URL to retrieve its SSL certificate information:");
    }

    public static URL getValidURLFromUser() {

        String userInput = getAndReturnUserInput();
        boolean validInput = false;
        URL url = null;

        while (!validInput) {
            try {
                url = getURLFromString(userInput);
                validInput = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Please enter a new URL:");
                userInput = getAndReturnUserInput();
            }
        }
        return url;
    }

    private static String getAndReturnUserInput() {
        return in.nextLine();
    }
}
