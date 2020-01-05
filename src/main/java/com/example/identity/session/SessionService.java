package com.example.identity.session;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Named;

import com.example.identity.session.model.Session;
import com.example.identity.session.repository.SessionRepository;
import com.example.identity.user.model.User;
import com.example.identity.user.passwordService.LoginException;
import com.example.identity.user.passwordService.LoginException.LoginExceptionType;
import com.example.identity.user.passwordService.PasswordService;
import com.example.identity.user.repository.UserRepository;
import org.springframework.http.ResponseCookie;

@Named
public class SessionService {

    public static final String AUTH_COOKIE = "sid";
    private static final int HOURS = 24;

    @Inject
    private SessionRepository sessionRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private PasswordService passwordService;

    //this has to be changed from localhost"
    private String domain = "identity.dev";

    public Session login(String email, String password) {
        var startTime = System.currentTimeMillis();
        User user = null;
        Session toReturn = null;

        try {
            try {
                user = userRepository.findUserByEmail(email.toLowerCase());
            } catch (Exception e) {
                throw new LoginException(LoginExceptionType.INVALID);
            }
            //todo check if user status is locked
            //todo check user login attempts

            if (!passwordService.validatePasswords(user.getPassword(), password)) {

                throw new LoginException(LoginExceptionType.INVALID);
            }

            return createSession(user.getUuid());
        } catch (Exception e) {
            throw e;

        } finally {
            //todo record login attempt
        }
    }

    public Session createSession(UUID userUuid) {

        Instant currentTime = Instant.now();
        Session session = Session.builder()
            .userUuid(userUuid)
            .createdAt(currentTime)
            .expiration(currentTime.plus(HOURS, ChronoUnit.HOURS))
            .build();
        return sessionRepository.save(session);
    }

    public String createAuthCookie(String jwt, Duration maxAge, boolean secure) {
        return ResponseCookie.from(AUTH_COOKIE, jwt)
            .httpOnly(true)
            .secure(secure)
            .maxAge(maxAge)
            .domain(domain)
            .path("/")
            .sameSite("Strict")
            .build()
            .toString();
    }

    public String deleteAuthCookie(boolean secure) {
        return ResponseCookie.from(AUTH_COOKIE, "")
            .httpOnly(true)
            .secure(secure)
            .maxAge(0)
            .domain(domain)
            .path("/")
            .sameSite("Strict")
            .build()
            .toString();
    }
}
