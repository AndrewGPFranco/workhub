package com.agpf.workhub.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(nullable = false, name = "first_name")
    private String firstName;

    @NotBlank
    @Column(nullable = false, name = "full_name")
    private String fullName;

    @NotBlank
    @Column(nullable = false, unique = true, updatable = false, name = "username")
    private String username;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true, updatable = false, name = "email")
    private String email;

    @NotBlank
    @Column(nullable = false, name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<RoleType> roles;

    @NotNull
    @Column(nullable = false, name = "date_birth", updatable = false)
    private LocalDate dateBirth;

    @NotNull
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public User(String username, String fullName, String firstName, LocalDate dateBirth, Set<RoleType> roles, String password, String email) {
        this.username = username;
        this.fullName = fullName;
        this.firstName = firstName;
        this.dateBirth = dateBirth;
        this.roles = roles;
        this.password = password;
        this.email = email;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @JsonIgnore
    public RoleType getRolePrincipal() {
        Set<RoleType> rolesPrincipal = getRoles();
        return RoleType.getRolePrincipal(rolesPrincipal);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", dateBirth=" + dateBirth +
                ", createdAt=" + createdAt +
                '}';
    }
}
