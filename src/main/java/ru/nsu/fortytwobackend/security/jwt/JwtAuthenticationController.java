package ru.nsu.fortytwobackend.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fortytwobackend.security.AuthService;
import ru.nsu.fortytwobackend.security.jwt.dto.JwtRefreshRequestDto;
import ru.nsu.fortytwobackend.security.jwt.dto.JwtRefreshResponseDto;


@RestController
@RequestMapping("/v1/jwt-auth")
@RequiredArgsConstructor
public class JwtAuthenticationController {
    private final AuthService authService;

    @PostMapping("refresh")
    public ResponseEntity<JwtRefreshResponseDto> getNewAccessToken(@RequestBody final JwtRefreshRequestDto request) {
        final JwtRefreshResponseDto response = authService.refreshAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }
}
