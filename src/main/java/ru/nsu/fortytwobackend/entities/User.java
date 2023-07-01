package ru.nsu.fortytwobackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    //Всегда должно совпадать с идентификатором в vk
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username")
    private String username;

    //не 3 НФ
    @Embedded
    private VkToken vkToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<JwtRefreshToken> tokens = new HashSet<>();
}
