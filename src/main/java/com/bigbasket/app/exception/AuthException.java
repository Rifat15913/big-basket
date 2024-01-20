package com.bigbasket.app.exception;

public class AuthException extends IllegalArgumentException {
    public AuthException(String message) {
        super(message);
    }
}
