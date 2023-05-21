package main;

import java.net.URL;
import java.security.cert.Certificate;

import static main.InputHandler.*;
import static main.SSLCertificateReader.printInfoOfSSLCertificates;
import static main.SSLCertificateReader.readAndReturnSSLCertificates;

public class Application {

    public static void main(String[] args) {
        printInputInstructions();
        URL url = getValidURLFromUser();
        Certificate[] certificates = readAndReturnSSLCertificates(url);
        printInfoOfSSLCertificates(certificates);
    }
}