package ru.nsu.fortytwobackend.security.exceptions;

import lombok.NonNull;

public class RefreshException extends RuntimeException{
    public RefreshException(@NonNull String message){
        super(message);
    }
}
