package com.agpf.workhub.dtos.notes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record OutputNoteDTO(
        @NotNull UUID id,
        @NotBlank String title,
        boolean isArchived,
        boolean isPinned,
        String content,
        @NotNull LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
