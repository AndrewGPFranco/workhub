package com.agpf.workhub.infrastructure.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginDTO(
        @NotBlank String usernameOrEmail,
        @NotBlank String password
) {
}
