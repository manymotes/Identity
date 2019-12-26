package com.example.identity.session;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import javax.inject.Named;

import com.example.identity.session.model.Session;
import org.springframework.http.ResponseCookie;

@Named
public class SessionService {

    public static final String AUTH_COOKIE = "sid";
    private static final int HOURS = 24;

    public Session login(String email, String password) {
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
