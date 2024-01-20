package com.bigbasket.app.exception;

public class ProductDoesNotExistException extends IllegalArgumentException {
    public ProductDoesNotExistException(String message) {
        super(message);
    }
}
