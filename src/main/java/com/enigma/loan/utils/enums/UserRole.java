package com.enigma.loan.utils.enums;

public enum UserRole {
    CUSTOMER,
    ADMIN;

    public static boolean isValid(String value) {
        if (value == null) {
            return false;
        }

        for (UserRole userRole : UserRole.values()) {
            if (userRole.name().equals(value)) {
                return true;
            }
        }

        return false;
    }
}
