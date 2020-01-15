package com.example.identity.passwordService;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.identity.user.passwordService.PasswordService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

public class PasswordServiceTest {

    private final static String PASSWORD = "Password123!";
    private final static String INCORRECT_PASSWORD = "wrong_password_dummy";
    private static final String EMAIL = "test@gmail.com";
    private static final String FIRST_NAME = "Nick";
    private static final String LAST_NAME = "Miller";

    @InjectMocks private PasswordService passwordService = new PasswordService();

    @Test
    public void testHashPassword() {
        String hash = passwordService.hashPassword(PASSWORD);
        assertThat(hash.startsWith("$argon2id$")).isEqualTo(true);
        assertThat(passwordService.validatePasswords(hash, PASSWORD)).isEqualTo(true);
        assertThat(passwordService.validatePasswords(hash, INCORRECT_PASSWORD)).isEqualTo(false);
    }
}
