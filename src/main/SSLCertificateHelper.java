package main;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SSLCertificateHelper {

    private SSLCertificateHelper() {}

    public static Certificate[] getSSLCertificates(URL url) {
        Certificate[] certificates = new Certificate[0];
        try {
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.connect();
            certificates = conn.getServerCertificates();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return certificates;
    }

    public static void printInfoOfSSLCertificates(Certificate[] certificates) {
        int counter = 1;
        for (Certificate certificate : certificates) {
            if(certificate instanceof X509Certificate x509Certificate) {
                System.out.printf("%n------------------------- Certificate %d -------------------------%n", counter++);
                printCertificateInfo(x509Certificate);
                System.out.printf("------------------------------------------------------------------%n");
            }
        }
    }

    private static void printCertificateInfo(X509Certificate certificate) {
        String outLiteral = "%-19s%-36s%n";
        System.out.printf(outLiteral, "Common Name:", getCommonName(certificate));
        System.out.printf(outLiteral, "", "");
        System.out.printf(outLiteral, "Issuer:", certificate.getIssuerX500Principal().getName());
        System.out.printf(outLiteral, "", "");
        System.out.printf("%-19s%2td.%2$tm.%2$ty - %3$td.%3$tm.%3$ty %n", "Validity Period:", certificate.getNotBefore(), certificate.getNotAfter());
        System.out.printf(outLiteral, "", "");
        System.out.printf(outLiteral, "Signature:", certificate.getSignature());
        System.out.printf(outLiteral, "", "");
        System.out.printf(outLiteral, "Alternative Names:", getSubjectAlternativeNames(certificate));
        System.out.printf(outLiteral, "", "");
        System.out.printf(outLiteral, "Key Usage:", getKeyUsage(certificate));
        System.out.printf(outLiteral, "", "");
        System.out.printf(outLiteral, "Public Key:", certificate.getPublicKey());
    }

    private static String getCommonName(X509Certificate certificate) {
        return certificate.getSubjectX500Principal().getName();
    }

    private static String getSubjectAlternativeNames(X509Certificate certificate) {
        try {
            Collection<List<?>> altNames = certificate.getSubjectAlternativeNames();
            if (altNames == null) return "None";

            String suffix = (altNames.size() > 5) ? ", ..." : "";

            return altNames.stream()
                    .limit(5)
                    .map(name -> (String) name.get(1))
                    .collect(Collectors.joining(", ")) + suffix;

        } catch (CertificateParsingException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    private static String getKeyUsage(X509Certificate certificate) {
        boolean[] keyUsage = certificate.getKeyUsage();
        if (keyUsage == null) return "None";
        StringBuilder builder = new StringBuilder();
        if (keyUsage[0]) builder.append("Digital Signature, ");
        if (keyUsage[1]) builder.append("Non-Repudiation, ");
        if (keyUsage[2]) builder.append("Key Encipherment, ");
        if (keyUsage[3]) builder.append("Data Encipherment, ");
        if (keyUsage[4]) builder.append("Key Agreement, ");
        if (keyUsage[5]) builder.append("Key Cert Sign, ");
        if (keyUsage[6]) builder.append("CRL Sign, ");
        if (keyUsage[7]) builder.append("Encipher Only, ");
        if (keyUsage[8]) builder.append("Decipher Only");
        if (builder.length() > 0) {
            builder.delete(builder.length()-2, builder.length());
        }
        return builder.toString();
    }
}
