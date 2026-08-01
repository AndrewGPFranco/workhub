package com.agpf.workhub.dtos.notes;

import com.agpf.workhub.models.notes.Note;
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

    public static OutputNoteDTO fromEntity(Note entity) {
        return new OutputNoteDTO(entity.getId(), entity.getTitle(), entity.isArchived(),
                entity.isPinned(), entity.getContent(), entity.getCreatedAt(), entity.getUpdatedAt());
    }

}
