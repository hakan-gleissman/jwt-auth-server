package se.sprinto.hakan.springboot2.keygen;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

public class JwtKeyGenerator {

    public static void main(String[] args) throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);

        KeyPair keyPair = generator.generateKeyPair();

        String privateKey = Base64.getEncoder()
                .encodeToString(keyPair.getPrivate().getEncoded());

        String publicKey = Base64.getEncoder()
                .encodeToString(keyPair.getPublic().getEncoded());

        System.out.println("Copy these values into application.properties or your environment:");
        System.out.println();
        System.out.println(privateKey);
        System.out.println(publicKey);
    }
}
