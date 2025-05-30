package com.PantryPal.service;


import io.jsonwebtoken.Jwts;
import jakarta.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

public class JwtSecretTest {
    @Test
    public void generateSecretKey(){
        SecretKey key = Jwts.SIG.HS512.key().build(); // SIG.<Encryption Algos>.key(). Example algos: HS512, SHA256, etc.
        String base64_key = DatatypeConverter.printBase64Binary(key.getEncoded());
        String encodedKey = DatatypeConverter.printHexBinary(key.getEncoded());
        System.out.printf("\nHexBinary Key = [%s]\n", encodedKey);
        System.out.printf("\nBase64Key = [%s]\n", base64_key);

        System.out.println(encodedKey);
    }
}
