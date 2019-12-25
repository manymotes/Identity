package com.example.identity.user.passwordService;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public class LoginException extends RuntimeException {

    @Getter
    private final LoginExceptionType type;
    @Getter
    private final boolean clearCookie;

    public LoginException(final LoginExceptionType type) {
        this(type, true);
    }

    public LoginException(final LoginExceptionType type, boolean clearCookie) {
        super(type.name());
        this.type = type;
        this.clearCookie = clearCookie;
    }

    public Map<String, Object> getExtensionMap() {
        Map<String, Object> extension = new HashMap<>();
        extension.put("type", type);
        return extension;
    }

    public enum LoginExceptionType {
        DISABLED,
        LOCKED,
        INVALID
    }
}
