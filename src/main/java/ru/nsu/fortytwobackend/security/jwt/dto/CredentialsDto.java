package ru.nsu.fortytwobackend.security.jwt.dto;

import lombok.NonNull;

public record CredentialsDto(
    @NonNull String username,
    @NonNull String password
){}