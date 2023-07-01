package ru.nsu.fortytwobackend.security.oauth2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResourceAccessTokenDto {
    @JsonProperty("access_token")
    @NonNull
    String accessToken;

    @JsonProperty("user_id")
    @NonNull
    String userId;

    @JsonProperty("expires_in")
    @NonNull
    Long expiresInSeconds;
}
