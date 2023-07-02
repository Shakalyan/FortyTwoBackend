package ru.nsu.fortytwobackend.security;

import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.fortytwobackend.entities.JwtRefreshToken;
import ru.nsu.fortytwobackend.entities.User;
import ru.nsu.fortytwobackend.entities.VkToken;
import ru.nsu.fortytwobackend.repositories.UserRepository;
import ru.nsu.fortytwobackend.security.exceptions.LoginException;
import ru.nsu.fortytwobackend.security.exceptions.RefreshException;
import ru.nsu.fortytwobackend.security.jwt.JwtProvider;
import ru.nsu.fortytwobackend.security.jwt.dto.JwtLoginResponseDto;
import ru.nsu.fortytwobackend.security.jwt.dto.JwtRefreshResponseDto;
import ru.nsu.fortytwobackend.security.oauth2.dto.ResourceAccessTokenDto;
import ru.nsu.fortytwobackend.vk.VkService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Set;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class AuthService implements UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final VkService vkService;

    @Transactional
    public @NonNull JwtLoginResponseDto login(final @NonNull ResourceAccessTokenDto resourceAccessTokenDto) {
        final Supplier<LoginException> loginExceptionSupplier = () -> new LoginException("Failed to login");

        final Long userId = Long.valueOf(resourceAccessTokenDto.getUserId());
        final var userOpt = userRepository.findById(userId);
        if(userOpt.isEmpty()){
            final var userInfo = vkService.getUserInfoById(userId, resourceAccessTokenDto.getAccessToken())
                    .orElseThrow(loginExceptionSupplier);

            final User user = new User();
            user.setId(userId);
            user.setUsername("%s_%s".formatted(userInfo.getFirstName(), userInfo.getLastName()));
            user.setFirstName(userInfo.getFirstName());
            user.setLastName(userInfo.getLastName());

            final Timestamp vkTokenExpirationTime = resourceAccessTokenDto.getExpiresInSeconds() == 0?
                    Timestamp.from(Instant.MAX) :
                    Timestamp.from(Instant.now().plusSeconds(resourceAccessTokenDto.getExpiresInSeconds()));

            user.setVkToken(new VkToken(resourceAccessTokenDto.getAccessToken(), vkTokenExpirationTime));
            userRepository.save(user);
        }

        final User user = userRepository.findById(userId)
                .orElseThrow(loginExceptionSupplier);

        final Set<Role> userRoles = Set.of(Role.USER);
        final JwtLoginResponseDto jwtLoginResponseDto = generateJwtResponse(user, userRoles);
        final JwtRefreshToken jwtRefreshToken = JwtRefreshToken.builder()
                .refreshToken(jwtLoginResponseDto.getRefreshToken())
                .user(user)
                .build();
        user.getTokens().add(jwtRefreshToken);
        userRepository.save(user);
        return jwtLoginResponseDto;
    }

    @Transactional(readOnly = true)
    public @NonNull JwtRefreshResponseDto refreshAccessToken(@NonNull final String refreshToken) {
        final Supplier<RefreshException> refreshExceptionSupplier = () -> new RefreshException("Refresh failed");

        if (!jwtProvider.validateRefreshToken(refreshToken)) {
            throw refreshExceptionSupplier.get();
        }

        final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
        final String username = claims.getSubject();

        final User user = userRepository.getByUsername(username)
                .orElseThrow(refreshExceptionSupplier);

        final boolean isJwtTokenExists = user.getTokens().stream()
                .map(JwtRefreshToken::getRefreshToken)
                .anyMatch(refreshToken::equals);

        if (!isJwtTokenExists) {
            throw refreshExceptionSupplier.get();
        }

        final Set<Role> userRoles = Set.of(Role.USER);
        final String accessToken = jwtProvider.generateAccessToken(user.getUsername(), userRoles);
        return new JwtRefreshResponseDto(accessToken);
    }

    @Override
    public @NonNull User getCurrentUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.getByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + "not found"));
    }

    private @NonNull JwtLoginResponseDto generateJwtResponse(@NonNull final User user,
                                                             @NonNull final Set<Role> roles) {
        final String accessToken = jwtProvider.generateAccessToken(user.getUsername(), roles);
        final String refreshToken = jwtProvider.generateRefreshToken(user.getUsername());
        return new JwtLoginResponseDto(accessToken, refreshToken);
    }
}
