package com.PantryPal.auth;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

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