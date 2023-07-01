package ru.nsu.fortytwobackend.security.jwt.dto;

import lombok.NonNull;
import lombok.Value;

@Value
public class JwtRefreshResponseDto {
    @NonNull String accessToken;
}
