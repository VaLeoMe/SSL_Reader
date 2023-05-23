package main;

import java.net.URL;
import java.security.cert.Certificate;

import static main.InputHandler.*;
import static main.SSLCertificateReader.printInfoOfSSLCertificates;
import static main.SSLCertificateReader.readAndReturnSSLCertificates;

public class Application {

    public static void main(String[] args) {
        printInputInstructionsStart();
        URL url = getValidURLFromUser();
        Certificate[] certificates = readAndReturnSSLCertificates(url);
        while (certificates.length == 0) {
            printInputInstructionsWrongURL();
            url = getValidURLFromUser();
            certificates = readAndReturnSSLCertificates(url);
        }
        printInfoOfSSLCertificates(certificates);

        printInputInstructionsEnd();
        boolean userWantsToContinue = checkIfUserWantsToContinue();
        while (userWantsToContinue) {
            printInputInstructionsContinue();
            url = getValidURLFromUser();
            certificates = readAndReturnSSLCertificates(url);
            while (certificates.length == 0) {
                printInputInstructionsWrongURL();
                url = getValidURLFromUser();
                certificates = readAndReturnSSLCertificates(url);
            }
            printInfoOfSSLCertificates(certificates);
            printInputInstructionsEnd();
            userWantsToContinue = checkIfUserWantsToContinue();
        }
    }
}