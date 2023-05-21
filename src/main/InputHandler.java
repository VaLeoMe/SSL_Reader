package main;

import java.util.Scanner;

public class InputHandler {

    private InputHandler() {}

    public static void logInputInstructions() {
        System.out.println("Welcome to the SSL certificate reader! :-)");
        System.out.println("Please enter a URL to retrieve its SSL certificate information:");
    }

    public static String getAndReturnUserInput() {
        Scanner in = new Scanner(System.in);

        return in.nextLine();
    }
}
