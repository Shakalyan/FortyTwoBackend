package ru.nsu.fortytwobackend.security.exceptions;

public class SignUpException extends RuntimeException{
    public SignUpException(String message) {
        super(message);
    }
}
