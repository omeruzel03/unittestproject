package com.ceng557.assignment.modules.error;

public class QuotaFullException extends RuntimeException {
    public QuotaFullException(String message) {
        super(message);
    }
}
