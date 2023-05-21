package main;

import static main.InputHandler.*;

public class Application {

    public static void main(String[] args) {
        logInputInstructions();
        String userInput = getAndReturnUserInput();
        verifyUserInput(userInput);
    }
}