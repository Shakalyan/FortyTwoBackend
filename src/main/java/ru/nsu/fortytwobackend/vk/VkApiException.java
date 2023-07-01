package ru.nsu.fortytwobackend.vk;

import lombok.NonNull;

public class VkApiException extends RuntimeException{
    public VkApiException(@NonNull String message){
        super(message);
    }

    public VkApiException(@NonNull Throwable cause){
        super(cause);
    }
}
