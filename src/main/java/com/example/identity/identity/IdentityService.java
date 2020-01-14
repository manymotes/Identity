package com.example.identity.identity;

import java.util.UUID;
import java.util.function.Supplier;
import javax.inject.Named;

import com.example.identity.identity.model.IdentityAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@Named
public class IdentityService {
    private static final ThreadLocal<String> customerOverride = new ThreadLocal<>();

    public UUID getCurrentSession() {
        IdentityAuthenticationToken auth = getAuthentication();
        if (auth == null || auth.getToken() == null) {
            return null;
        }
        return UUID.fromString(auth.getToken().getId());
    }

    public UUID getCurrentUser() {
        IdentityAuthenticationToken auth = getAuthentication();
        if (auth == null || auth.getToken() == null) {
            return null;
        }
        return UUID.fromString(auth.getToken().getSubject());
    }

    public String getCurrentCustomer() {
        String override = customerOverride.get();
        if (override != null) {
            return override;
        }
        IdentityAuthenticationToken auth = getAuthentication();
        return auth != null ? auth.getCustomerId() : null;
    }

    public String getCurrentToken() {
        IdentityAuthenticationToken auth = getAuthentication();
        if (auth == null || auth.getToken() == null) {
            return null;
        }
        return auth.getToken().getToken();
    }

    public void runAsCustomer(String customerId, Runnable runnable) {
        try {
            customerOverride.set(customerId);
            runnable.run();
        } finally {
            customerOverride.remove();
        }
    }

    public <T> T runAsCustomer(String customerId, Supplier<T> runnable) {
        try {
            customerOverride.set(customerId);
            return runnable.get();
        } finally {
            customerOverride.remove();
        }
    }

    private IdentityAuthenticationToken getAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SecurityContext securityContext = SecurityContextHolder.getContext();

        return auth instanceof IdentityAuthenticationToken ? (IdentityAuthenticationToken)auth : null;
    }
}
