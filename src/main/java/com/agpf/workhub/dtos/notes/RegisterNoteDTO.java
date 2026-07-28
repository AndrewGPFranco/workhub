package com.agpf.workhub.dtos.notes;

import com.agpf.workhub.models.notes.Note;
import com.agpf.workhub.models.subdomains.Subdomain;
import com.agpf.workhub.models.user.User;
import com.agpf.workhub.utils.DateUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegisterNoteDTO(
        @NotBlank(message = "O título é obrigatório.") String title,
        @NotNull(message = "O subdomínio é obrigatório.") UUID idSubdomain,
        String content,
        UUID idNote
) {

    public static Note toEntity(RegisterNoteDTO dto, User user, Subdomain subdomain) {
        return Note.builder().title(dto.title()).version(0).isArchived(false)
                .createdAt(DateUtils.getLocalDateTimeAmericaSP())
                .isPinned(false).user(user).subdomain(subdomain).content(dto.content()).build();
    }

}
