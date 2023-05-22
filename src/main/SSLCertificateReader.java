package main;

import javax.net.ssl.HttpsURLConnection;
import java.math.BigInteger;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        int counter = 1;
        for (Certificate certificate : certificates) {
            if(certificate instanceof X509Certificate x509Certificate) {
                System.out.printf("%n------------------------- Certificate %d -------------------------%n", counter++);
                printCertificateInfo(x509Certificate);
                System.out.printf("------------------------------------------------------------------%n");
            }
        }
    }

    public static void printCertificateInfo(X509Certificate certificate) {
        System.out.printf("%-17s%-36s%n", "Common Name:", getCommonName(certificate));
        System.out.printf("%-17s%-36s%n", "", "");
        System.out.printf("%-17s%-36s%n", "Issuer:", certificate.getIssuerX500Principal().getName());
        System.out.printf("%-17s%-36s%n", "", "");
        System.out.printf("%-17s%2td.%2$tm.%2$ty - %3$td.%3$tm.%3$ty %n", "Validity Period:", certificate.getNotBefore(), certificate.getNotAfter());
        System.out.printf("%-17s%-36s%n", "", "");
        System.out.printf("%-17s%-36s%n", "Signature:", certificate.getSignature());
        System.out.printf("%-17s%-36s%n", "", "");
        printSubjectAlternativeNames(certificate);
        System.out.printf("%-17s%-36s%n", "", "");
        System.out.printf("%-17s%-36s%n", "Key Usage:", getKeyUsage(certificate));
        System.out.printf("%-17s%-36s%n", "", "");
        System.out.printf("%-17s%-36s%n", "Public Key:", certificate.getPublicKey());
        //dissectAndPrintPublicKey(certificate);
        //System.out.println("Common Name: " + getCommonName(certificate));
        //System.out.println("Issuer: " + certificate.getIssuerX500Principal().getName());
        //System.out.print("Validity Period: ");
        //System.out.printf("%1$td.%1$tm.%1$ty - %2$td.%2$tm.%2$ty %n", certificate.getNotBefore(), certificate.getNotAfter());
        //System.out.println("Signature: " + certificate.getSignature());
        //System.out.println("Subject Alternative Names: " + getSubjectAlternativeNames(certificate));
        //System.out.println("Key Usage: " + getKeyUsage(certificate));
        //System.out.println("Public Key: " + certificate.getPublicKey());
    }

    private static String getCommonName(X509Certificate certificate) {
        return certificate.getSubjectX500Principal().getName();
    }

    private static void dissectAndPrintPublicKey(X509Certificate certificate){
        RSAPublicKey rsaPublicKey = (RSAPublicKey) certificate.getPublicKey();
        if (rsaPublicKey.getParams() == null) {
            System.out.printf("  %-17s%-28s%n", "params:", "None");
        } else {
            System.out.printf("  %-17s%-28s%n", "params:", rsaPublicKey.getParams());
        }
        BigInteger modulus = rsaPublicKey.getModulus();
        String modulusString = modulus.toString();
        if (modulusString.length() > 20) {
            modulusString = modulusString.substring(0, 20) + "...";
        }
        System.out.printf("  %-17s%-28s%n", "modulus:", modulusString);
        System.out.printf("  %-17s%-28s%n", "public exponent:", rsaPublicKey.getPublicExponent());
    }

    public static void printSubjectAlternativeNames(X509Certificate certificate) {
        System.out.printf("%-17s%-36s%n", "Subject Alter-:", getSubjectAlternativeNames(certificate));
        System.out.printf("%-17s%-36s%n", "native Names", "");
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

    //private static String getSubjectAlternativeNames(X509Certificate certificate) {
    //    try {
    //        Collection<List<?>> altNames = certificate.getSubjectAlternativeNames();
    //        if (altNames == null) return "None";
    //
    //        Stream<String> altNamesStream = altNames.stream()
    //                .map(name -> (String) name.get(1));
//
    //        int altNamesCount = (int) altNamesStream.count();
    //
    //        String suffix = (altNamesCount > 5) ? ", ..." : "";
//
    //        return altNamesStream
    //                .limit(5)
    //                .collect(Collectors.joining(", ")) + suffix;
//
    //    } catch (CertificateParsingException e) {
    //        e.printStackTrace();
    //        return "Error";
    //    }
    //}


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
