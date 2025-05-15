package com.PantryPal.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AuthConfigTest {
    private final String TEST_STR = "testingString";

    @Autowired
    private PasswordEncoder encoder;

    @Test
    void generateBCryptPasswordEncoder() {
        String expectedResult = encoder.encode(TEST_STR);
        assertTrue(encoder.matches(TEST_STR,expectedResult));
    }
}