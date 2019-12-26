package com.example.identity.authentication;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.identity.authentication.model.LoginResponse;
import com.example.identity.session.SessionService;
import com.example.identity.session.model.Session;
import com.example.identity.user.passwordService.JwtService;

public class AuthenticationMutationResolver {

    @Inject
    private SessionService sessionService;

    @Inject
    private JwtService jwtService;

    @Inject
    private HttpServletRequest request;

    @Inject
    private HttpServletResponse response;

    public LoginResponse login(String email, String password) {
        Session session = sessionService.login(email, password);

        String jwt = jwtService.buildToken(session.getUuid(), session.getUserUuid(), session.getCreatedAt(), session.getExpiration());
        Duration maxAge = Duration.of(session.getExpiration().toEpochMilli() - session.getCreatedAt().toEpochMilli(), ChronoUnit.MILLIS);
        String cookie = sessionService.createAuthCookie(jwt, maxAge, request.isSecure());
        response.addHeader(SET_COOKIE, cookie);

        return createAuthResponse(session, null, false);
    }
}
