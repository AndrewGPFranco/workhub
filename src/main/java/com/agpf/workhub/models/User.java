package com.agpf.workhub.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotBlank
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotBlank
    @Max(value = 30)
    @Column(name = "username", unique = true, nullable = false, length = 30)
    private String username;

    @NotBlank
    @Column(name = "last_name", nullable = false, length = 40)
    private String lastName;

    @NotBlank
    @Column(name = "first_name", nullable = false, length = 40)
    private String firstName;

    @NotBlank
    @Column(name = "keycloak_id", unique = true, nullable = false)
    private String keycloakId;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
