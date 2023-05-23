package main;

import java.net.URL;
import java.security.cert.Certificate;

import static main.InputHandler.*;
import static main.InputHandler.getNewURLFromUser;
import static main.SSLCertificateHelper.printInfoOfSSLCertificates;
import static main.SSLCertificateHelper.getSSLCertificates;

public class Application {

    public static void main(String[] args) {

        printWelcomeLine();
        URL url = getURLFromUser();
        Certificate[] certificates = getSSLCertificates(url);
        while (certificates.length == 0) {
            url = getNewURLFromUser();
            certificates = getSSLCertificates(url);
        }
        printInfoOfSSLCertificates(certificates);
        printInputInstructionsEnd();
        boolean userWantsToContinue = checkIfUserWantsToContinue();

        while (userWantsToContinue) {
            printInputInstructionsBasic();
            url = getURLFromUser();
            certificates = getSSLCertificates(url);
            printInfoOfSSLCertificates(certificates);
            printInputInstructionsEnd();
            userWantsToContinue = checkIfUserWantsToContinue();
        }
    }

}