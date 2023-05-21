package main;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

public class SSLCertificateReader {

    private SSLCertificateReader() {}

    public static Certificate[] readAndReturnSSLCertificates(URL url) {
        Certificate[] certificates = new Certificate[0];
        try {
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.connect();
            certificates = conn.getServerCertificates();
        } catch (Exception e) {
            new RuntimeException(e.getMessage());
        }
        return certificates;
    }

    public static void printInfoOfSSLCertificates(Certificate[] certificates) {
        for (Certificate certificate : certificates) {
            System.out.println("Public Key: " + certificate.getPublicKey());
            if(certificate instanceof X509Certificate x) {
                System.out.println("IssuerDN: " + x.getIssuerDN());
                System.out.println("IssuerX500Principal: " + x.getIssuerX500Principal());
                System.out.println("Public Key: " + x.getPublicKey());

            }
        }
    }
}
