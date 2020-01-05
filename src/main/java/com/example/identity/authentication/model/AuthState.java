package com.example.identity.authentication.model;

public enum AuthState {
    NOT_AUTHENTICATED,
    VALIDATE_EMAIL,
    FORCE_PASSWORD_CHANGE,
//    SETUP_MFA,
//    LOGIN_MFA,
    AUTHENTICATED;

    public boolean isNotAuthenticated() {
        return this == NOT_AUTHENTICATED;
    }

    public boolean isAuthenticated() {
        return this == AUTHENTICATED;
    }

    public boolean supportsValidateMode() {
        return this == AUTHENTICATED || this == NOT_AUTHENTICATED;
    }
}
