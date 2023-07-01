package ru.nsu.fortytwobackend.security.oauth2;

import lombok.NonNull;
import ru.nsu.fortytwobackend.security.oauth2.dto.ResourceAccessTokenDto;

public interface AuthorizationProvider {
    @NonNull String oauth2Uri(@NonNull String callbackUri);

    @NonNull ResourceAccessTokenDto getAccessTokenWithCode(@NonNull String callbackUri,
                                                           @NonNull String code);

    @NonNull String clientId();

    @NonNull String clientSecret();
}
