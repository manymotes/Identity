package com.example.identity.authentication;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.identity.authentication.model.AuthState;
import com.example.identity.authentication.model.LoginResponse;
import com.example.identity.session.SessionService;
import com.example.identity.session.model.Session;
import com.example.identity.user.passwordService.JwtService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Named
//@Component
public class AuthenticationMutationResolver implements GraphQLMutationResolver {

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

        //todo check if user needs to verify their email
        return LoginResponse.builder()
            .authState(AuthState.AUTHENTICATED)
            .userUuid(session.getUserUuid())
            .build();
    }
}
