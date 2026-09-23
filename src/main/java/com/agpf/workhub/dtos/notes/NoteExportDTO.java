package com.agpf.workhub.dtos.notes;

import org.springframework.core.io.ByteArrayResource;

public record NoteExportDTO(
        ByteArrayResource arquivo,
        String nomeArquivo
) {
}
