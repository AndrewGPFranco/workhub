package com.agpf.workhub.models.user;

import com.agpf.workhub.enums.plan.PlanResourceType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotBlank
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotBlank
    @Size(max = 30)
    @Column(name = "username", unique = true, nullable = false, length = 30)
    private String username;

    @NotBlank
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @NotBlank
    @Column(name = "role", nullable = false, length = 30)
    private String role;

    @NotBlank
    @Column(name = "last_name", nullable = false, length = 40)
    private String lastName;

    @NotBlank
    @Column(name = "first_name", nullable = false, length = 40)
    private String firstName;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "resources")
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "contracteds_resources", joinColumns = @JoinColumn(name = "user_id"))
    private List<PlanResourceType> contractedResources;

}
