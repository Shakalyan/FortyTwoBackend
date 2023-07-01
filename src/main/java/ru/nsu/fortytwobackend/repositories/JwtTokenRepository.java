package ru.nsu.fortytwobackend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fortytwobackend.entities.JwtRefreshToken;
import ru.nsu.fortytwobackend.entities.User;

import java.util.Set;

@Repository
public interface JwtTokenRepository extends CrudRepository<JwtRefreshToken, Long> {
    Set<JwtRefreshToken> findByUser(User user);
}
