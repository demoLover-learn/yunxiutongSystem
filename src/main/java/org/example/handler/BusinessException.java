package org.example.handler;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
