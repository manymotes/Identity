package com.example.identity.util.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        this("not found");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
