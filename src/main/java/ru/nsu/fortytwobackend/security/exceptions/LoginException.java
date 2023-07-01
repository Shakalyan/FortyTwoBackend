package ru.nsu.fortytwobackend.security.exceptions;

import lombok.NonNull;

public class LoginException extends RuntimeException{
    public LoginException(@NonNull String message){
        super(message);
    }
}
