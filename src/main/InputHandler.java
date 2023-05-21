package main;

import java.util.Scanner;

import static main.URLVerifier.verifyURL;

public class InputHandler {

    private static final Scanner in = new Scanner(System.in);

    private InputHandler() {}

    public static void printInputInstructions() {
        System.out.println("Welcome to the SSL certificate reader! :-)");
        System.out.println("Please enter a URL to retrieve its SSL certificate information:");
    }

    public static String getUserInputUntilValid() {

        String userInput = getAndReturnUserInput();
        boolean validInput = false;

        while (!validInput) {
            try {
                verifyURL(userInput);
                validInput = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Please enter a new one:");
                userInput = getAndReturnUserInput();
            }
        }

        return userInput;
    }

    private static String getAndReturnUserInput() {

        return in.nextLine();

    }
}
