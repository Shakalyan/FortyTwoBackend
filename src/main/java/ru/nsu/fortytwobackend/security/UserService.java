package ru.nsu.fortytwobackend.security;

import ru.nsu.fortytwobackend.entities.User;

public interface UserService {
    User getCurrentUser();
}
