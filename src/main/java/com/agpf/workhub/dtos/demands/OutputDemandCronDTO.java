package com.agpf.workhub.dtos.demands;

import java.time.LocalDateTime;
import java.util.UUID;

public record OutputDemandCronDTO(
        Long idUsuario,
        UUID idDemanda,
        String tituloTarefa,
        LocalDateTime createdAt
) {
}
