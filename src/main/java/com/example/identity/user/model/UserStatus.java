package com.example.identity.user.model;

public enum UserStatus {
    ACTIVE,
    DISABLED,
    LOCKED;

    public boolean isActive() {
        return this == ACTIVE;
    }
    public boolean isDisabled() {
        return this == DISABLED;
    }
    public boolean isLocked() {
        return this == LOCKED;
    }
}
