package com.agpf.workhub.infrastructure.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserRegisterDTO(
        @NotBlank String firstName,
        @NotBlank String fullname,
        @NotBlank String username,
        @Email String email,
        @NotBlank String password,
        @NotNull LocalDate dateBirth
) {
}
