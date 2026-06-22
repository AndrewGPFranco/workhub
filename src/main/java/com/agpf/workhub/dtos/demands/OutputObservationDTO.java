package com.agpf.workhub.dtos.demands;

import com.agpf.workhub.models.demands.Observation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record OutputObservationDTO(
        @NotBlank String textObservation,
        @NotNull Instant createdAt
) {

    public static OutputObservationDTO fromEntity(Observation entity) {
        return new OutputObservationDTO(entity.getTextObservation(), entity.getCreatedAt());
    }

}
