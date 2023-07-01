package ru.nsu.fortytwobackend.vk.dto;

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
public class VkUserInfoRequest {
    @JsonProperty("user_ids")
    @NonNull
    String userIds;

    @JsonProperty("access_token")
    @NonNull
    String accessToken;

    @JsonProperty("v")
    @NonNull
    String version;
}
