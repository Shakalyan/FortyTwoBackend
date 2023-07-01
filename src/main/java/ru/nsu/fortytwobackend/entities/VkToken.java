package ru.nsu.fortytwobackend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalTime;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class VkToken implements Serializable {
    @Column(name = "vk_token")
    private String token;

    @Column(name = "vk_token_expiration")
    private Timestamp expirationTime;
}
