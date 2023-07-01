package ru.nsu.fortytwobackend.security.oauth2;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VkAuthorizationProvider implements AuthorizationProvider {
    private static final String REQUEST_TEMPLATE =
            "https://oauth.vk.com/authorize?client_id=%s&display=page&redirect_uri=%s&scope=friends&response_type=code&v=5.131";

    private final @NonNull String clientId;
    private final @NonNull String clientSecret;

    public VkAuthorizationProvider(@Value("${oauth2.vk.clientid}") @NonNull final String clientId,
                                   @Value("${oauth2.vk.secret}") @NonNull final String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public @NonNull String oauth2Uri(@NonNull final String callbackUri) {
        return REQUEST_TEMPLATE.formatted(clientId, callbackUri);
    }

    @Override
    public @NonNull String clientId() {
        return clientId;
    }

    @Override
    public @NonNull String clientSecret() {
        return clientSecret;
    }
}
