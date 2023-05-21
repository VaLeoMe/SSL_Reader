package main;

import java.util.Scanner;

import static main.URLVerifier.verifyURL;

public class InputHandler {

    private InputHandler() {}

    public static String getUserInputUntilValid() {
        System.out.println("logic coming");
        return "result";
    }

    public static void logInputInstructions() {
        System.out.println("Welcome to the SSL certificate reader! :-)");
        System.out.println("Please enter a URL to retrieve its SSL certificate information:");
    }

    public static String getAndReturnUserInput() {

        Scanner in = new Scanner(System.in);
        return in.nextLine();

    }

    public static void verifyUserInput(String userInput) {
        try {
            verifyURL(userInput);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
