package ru.nsu.fortytwobackend.security.oauth2;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/v1/oauth2")
@RequiredArgsConstructor
@Log4j2
public class Oauth2Controller {
    private final AuthorizationProvider authorizationProvider;
    private @Value("back.baseuri") String backendBaseUri;

    @GetMapping
    void redirectToAuthServer(final HttpServletResponse response) throws IOException {
        response.sendRedirect(authorizationProvider.oauth2Uri("%s/v1/oauth2/callback".formatted(backendBaseUri)));
    }

    @GetMapping("callback")
    public void get(@RequestParam(name = "code", required = false) final String code){
        log.info("ouath 2 callback " + code);
    }
}
