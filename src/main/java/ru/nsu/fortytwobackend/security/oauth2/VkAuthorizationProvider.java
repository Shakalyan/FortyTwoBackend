package ru.nsu.fortytwobackend.security.oauth2;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.nsu.fortytwobackend.security.exceptions.LoginException;
import ru.nsu.fortytwobackend.security.oauth2.dto.ResourceAccessTokenDto;

@Component
public class VkAuthorizationProvider implements AuthorizationProvider {
    private static final String REQUEST_TEMPLATE =
            "https://oauth.vk.com/authorize?client_id=%s&display=page&redirect_uri=%s&scope=friends&response_type=code&v=5.131";

    private static final String GET_TOKEN_URI_TEMPLATE =
            "https://oauth.vk.com/access_token?client_id=%s&client_secret=%s&redirect_uri=%s&code=%s";

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
    public @NonNull ResourceAccessTokenDto getAccessTokenWithCode(@NonNull String callbackUri,
                                                                  @NonNull String code) {
        final var restTemplate = new RestTemplate();

        final var accessTokenDto = restTemplate
                .getForEntity(GET_TOKEN_URI_TEMPLATE.formatted(clientId, clientSecret, callbackUri, code),
                        ResourceAccessTokenDto.class)
                .getBody();

        if (accessTokenDto == null) {
            throw new LoginException("Vk auth fail");
        }

        return accessTokenDto;
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
