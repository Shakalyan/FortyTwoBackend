package ru.nsu.fortytwobackend.vk.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Jacksonized
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VkUserInfoDto {
    @JsonProperty("first_name")
    @NonNull
    String firstName;

    @JsonProperty("last_name")
    @NonNull
    String lastName;

    @JsonProperty("id")
    @NonNull
    String userId;

    @Value
    @Jacksonized
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VkUserInfoDtoList {
        List<VkUserInfoDto> response;
    }
}

