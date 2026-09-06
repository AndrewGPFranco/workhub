package com.agpf.workhub.dtos.sprints;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record InputSprintDTO(
        @NotBlank String title,
        @NotNull UUID idSubdomain,
        @NotNull LocalDate dateToUse
) {
}
