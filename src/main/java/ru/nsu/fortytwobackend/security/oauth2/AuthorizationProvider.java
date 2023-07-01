package ru.nsu.fortytwobackend.security.oauth2;

import lombok.NonNull;

public interface AuthorizationProvider {
    @NonNull String oauth2Uri(@NonNull String callbackUri);

    @NonNull String clientId();

    @NonNull String clientSecret();
}
