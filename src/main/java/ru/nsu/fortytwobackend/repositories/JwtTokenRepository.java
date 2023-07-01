package ru.nsu.fortytwobackend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fortytwobackend.entities.JwtToken;
import ru.nsu.fortytwobackend.entities.User;

import java.util.Set;

@Repository
public interface JwtTokenRepository extends CrudRepository<JwtToken, Long> {
    Set<JwtToken> findByUser(User user);
}
