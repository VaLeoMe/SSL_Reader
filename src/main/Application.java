package main;

import static main.InputHandler.*;

public class Application {

    public static void main(String[] args) {
        printInputInstructions();
        String userInput = getUserInputUntilValid();
    }
}