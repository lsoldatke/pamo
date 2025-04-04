package com.example.lab2.enums;

public enum Gender {
    MALE("Male"),
    FEMALE("Female");

    private final String label;

    Gender(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
