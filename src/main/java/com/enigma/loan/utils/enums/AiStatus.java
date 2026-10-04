package com.enigma.loan.utils.enums;

public enum AiStatus {
    PENDING,
    SUCCESS,
    FAILED;

    public static boolean isValid(String value) {
        if (value == null) {
            return false;
        }

        for (LoanStatus courseStatus : LoanStatus.values()) {
            if (courseStatus.name().equals(value)) {
                return true;
            }
        }

        return false;
    }
}
