package com.example.lab2.enums;

public enum PhysicalActivityLevel {
    LACK_OF_ACTIVITY("Lack of activity"),
    LITTLE("Little"),
    MODERATE("Moderate"),
    HIGH("High"),
    VERY_HIGH("Very high"),
    PROFESSIONAL("Professional");

    private final String label;

    PhysicalActivityLevel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
