package ru.rplaton.labforward.coding.challenge.dto;

public final class ApiError {

    private final String message;

    public ApiError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
