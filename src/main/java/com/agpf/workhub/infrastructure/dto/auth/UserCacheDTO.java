package com.agpf.workhub.infrastructure.dto.auth;

import com.agpf.workhub.domain.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCacheDTO(
        @NotNull User user,
        @NotBlank String code
) {
}
