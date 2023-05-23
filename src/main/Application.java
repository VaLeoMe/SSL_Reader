package main;

import java.net.URL;
import java.security.cert.Certificate;

import static main.InputHandler.*;
import static main.SSLCertificateHelper.printInfoOfSSLCertificates;
import static main.SSLCertificateHelper.getSSLCertificates;

public class Application {

    public static void main(String[] args) {
        printWelcomeLine();
        Certificate[] certificates = getValidSSLCertificates();
        boolean userWantsToContinue = true;
        while (userWantsToContinue) {
            while (certificates.length == 0) {
                certificates = getValidSSLCertificates();
            }
            printInfoOfSSLCertificates(certificates);
            printInputInstructionsEnd();
            userWantsToContinue = checkIfUserWantsToContinue();
            certificates = new Certificate[0];
        }
    }

    private static Certificate[] getValidSSLCertificates() {
        boolean validCertificates = false;
        URL url;
        Certificate[] certificates = new Certificate[0];
        while (!validCertificates) {
            printInputInstructionsBasic();
            url = getURLFromUser();
            try {
                certificates = getSSLCertificates(url);
                validCertificates = true;
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
        return certificates;
    }
}
