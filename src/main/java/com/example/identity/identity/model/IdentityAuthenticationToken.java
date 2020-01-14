package com.example.identity.identity.model;

import java.util.Collections;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class IdentityAuthenticationToken extends AbstractAuthenticationToken  {

    @Getter private final DecodedJWT token;
    @Getter private final String customerId;

    @Builder
    public IdentityAuthenticationToken(DecodedJWT token, String customerId) {
        super(Collections.emptyList());
        this.token = token;
        this.customerId = customerId;
        setAuthenticated(true);
    }

    public boolean hasUser() {
        return token != null && token.getSubject() != null;
    }

    public boolean hasCustomer() {
        return customerId != null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
