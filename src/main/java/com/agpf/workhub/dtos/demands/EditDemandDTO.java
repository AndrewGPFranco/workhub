package com.agpf.workhub.dtos.demands;

import com.agpf.workhub.enums.demands.PriorityDemandType;
import com.agpf.workhub.enums.demands.StatusDemandType;

import java.time.LocalDate;
import java.util.UUID;

public record EditDemandDTO(
        String title,
        String description,
        LocalDate deadline,
        StatusDemandType status,
        String observationsToReview,
        PriorityDemandType priority,
        LocalDate finalizedAt,
        UUID subdomainId
) {
}
