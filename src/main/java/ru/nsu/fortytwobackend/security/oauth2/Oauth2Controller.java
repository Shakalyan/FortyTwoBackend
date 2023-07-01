package ru.nsu.fortytwobackend.security.oauth2;

import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fortytwobackend.security.AuthService;
import ru.nsu.fortytwobackend.security.exceptions.LoginException;
import ru.nsu.fortytwobackend.security.jwt.dto.JwtLoginResponseDto;

import java.io.IOException;

@RestController
@RequestMapping("/v1/oauth2")
@RequiredArgsConstructor
@Log4j2
public class Oauth2Controller {
    private final AuthorizationProvider authorizationProvider;
    private final AuthService authService;
    private @Value("${back.baseuri}") String backendBaseUri;
    private @Value("${front.baseuri}") String frontendBaseUri;

    private @NonNull String getCallbackUrl(){
        return "%s/v1/oauth2/callback".formatted(backendBaseUri);
    }

    private @NonNull String getFrontendCallbackUrl(final @NonNull JwtLoginResponseDto responseDto){
        return "%s/login-callback?access=%s&refresh=%s"
                .formatted(frontendBaseUri, responseDto.getAccessToken(), responseDto.getRefreshToken());
    }

    @GetMapping
    void redirectToAuthServer(final HttpServletResponse response){
        try {
            response.sendRedirect(authorizationProvider.oauth2Uri(getCallbackUrl()));
        }
        catch (IOException exception){
            throw new LoginException(exception);
        }
    }

    @GetMapping("callback")
    public void get(@RequestParam(name = "code") final String code,
                    final HttpServletResponse response) {
        final var accessTokenDto = authorizationProvider.getAccessTokenWithCode(getCallbackUrl(), code);
        final var jwtLoginResponse = authService.login(accessTokenDto);
        log.info("User with id %s login".formatted(accessTokenDto.getUserId()));

        try {
            response.sendRedirect(getFrontendCallbackUrl(jwtLoginResponse));
        }
        catch (IOException exception){
            throw new LoginException(exception);
        }
    }
}
