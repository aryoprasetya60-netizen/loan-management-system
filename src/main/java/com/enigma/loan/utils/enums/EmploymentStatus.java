package com.enigma.loan.utils.enums;

public enum EmploymentStatus {
    EMPLOYED,
    SELF_EMPLOYED,
    CONTRACT,
    UNEMPLOYED;

    public static boolean isValid(String value) {
        if (value == null) {
            return false;
        }

        for (EmploymentStatus employmentStatus : EmploymentStatus.values()) {
            if (employmentStatus.name().equals(value)) {
                return true;
            }
        }

        return false;
    }
}
