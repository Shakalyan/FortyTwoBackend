package ru.nsu.fortytwobackend.controlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fortytwobackend.entities.User;
import ru.nsu.fortytwobackend.security.AuthService;
import ru.nsu.fortytwobackend.vk.dto.VkUserInfoDto;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Log4j2
public class UsersController {

    private final AuthService authService;

    @GetMapping("/current-info")
    public ResponseEntity<VkUserInfoDto> getNewAccessToken() {
        final User user = authService.getCurrentUser();
        return ResponseEntity.ok(new VkUserInfoDto(user.getFirstName(), user.getLastName(), user.getId().toString()));
    }
}
