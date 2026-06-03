package com.agpf.workhub.dtos.demands;

import com.agpf.workhub.enums.demands.PriorityDemandType;
import com.agpf.workhub.enums.demands.StatusDemandType;
import com.agpf.workhub.models.demands.Demand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record OutputDemandDTO(
        @NotNull UUID id,
        @NotBlank String title,
        @NotBlank String description,
        LocalDate deadline,
        @NotNull StatusDemandType status,
        @NotNull PriorityDemandType priority,
        @NotNull LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String observationsToReview,
        LocalDate finalizedAt
) {

    public static OutputDemandDTO fromEntity(Demand demand) {
        return OutputDemandDTO.builder().id(demand.getId()).title(demand.getTitle())
                .description(demand.getDescription()).deadline(demand.getDeadline()).finalizedAt(demand.getFinalizedAt())
                .status(demand.getStatus()).priority(demand.getPriority()).createdAt(demand.getCreatedAt())
                .updatedAt(demand.getUpdatedAt()).observationsToReview(demand.getObservationsToReview()).build();
    }

}
