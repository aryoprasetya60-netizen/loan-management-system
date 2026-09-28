package com.enigma.loan.utils.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s dengan %s '%s' tidak ditemukan", resourceName, fieldName, fieldValue)); // {user} dengan {id} {2} tidak ditemukan
    }
}
