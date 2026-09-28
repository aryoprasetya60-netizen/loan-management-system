package com.enigma.loan.utils.exception;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String resourceName, String resource) {
        super(String.format("%s: %s sudah dipakai", resourceName, resource)); // {email}: @mail sudah dipakai
    }
}
