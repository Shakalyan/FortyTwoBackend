package ru.nsu.fortytwobackend.vk;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.nsu.fortytwobackend.vk.dto.VkUserInfoDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VkService {
    private static final String VK_API_VERSION = "5.131";
    private static final String VK_API_BASE_URL = "https://api.vk.com/method";

    public @NonNull List<VkUserInfoDto> getUsersInfo(@NonNull final List<? extends Long> ids,
                                                     @NonNull final String accessToken) {
        final var restTemplate = new RestTemplate();
        final String idsJoining = ids
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        final var response = restTemplate.postForEntity(
                "%s/users.get?user_ids=%s&access_token=%s&v=%s"
                        .formatted(VK_API_BASE_URL, idsJoining, accessToken, VK_API_VERSION),
                null,
                VkUserInfoDto.VkUserInfoDtoList.class);

        VkUserInfoDto.VkUserInfoDtoList userInfoResponse = response.getBody();

        if (userInfoResponse == null || userInfoResponse.getResponse() == null) {
            throw new VkApiException("Empty answer for users.get");
        }

        return userInfoResponse.getResponse();
    }

    public @NonNull Optional<VkUserInfoDto> getUserInfoById(@NonNull final Long id,
                                                            @NonNull final String accessToken) {
        final var users = getUsersInfo(List.of(id), accessToken);
        if (users.size() > 1) {
            throw new VkApiException("Unexpected count of users");
        }
        return users.stream().findAny();
    }
}
