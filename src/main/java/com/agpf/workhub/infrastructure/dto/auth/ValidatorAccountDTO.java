package com.agpf.workhub.infrastructure.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record ValidatorAccountDTO(
        @NotBlank String code,
        @NotBlank String email
) {
}
