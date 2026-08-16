package com.agpf.workhub.dtos.demands;

import com.agpf.workhub.enums.demands.PriorityDemandType;
import com.agpf.workhub.enums.demands.SprintType;
import com.agpf.workhub.enums.demands.StatusDemandType;
import com.agpf.workhub.models.demands.Demand;
import com.agpf.workhub.models.subdomains.Subdomain;
import com.agpf.workhub.models.user.User;
import com.agpf.workhub.utils.DateUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record RegisterDemandDTO(
        @NotBlank String title,
        @NotBlank String description,
        LocalDate deadline,
        @NotNull StatusDemandType status,
        @NotNull PriorityDemandType priority,
        String observationToReview,
        UUID subdomainId,
        InputObservationDTO observations,
        @NotNull SprintType sprint
) {

    public Demand toEntity(User user, Subdomain subdomain) {
        var demand = Demand.builder().user(user).title(title).createdAt(DateUtils.getLocalDateTimeAmericaSP()).updatedAt(null)
                .observationsToReview(observationToReview).description(description).deadline(deadline).sprint(sprint)
                .observations(null).status(status).priority(priority).subdomain(subdomain).build();

        var observations = observations().textObservations().stream()
                .map(o -> InputObservationDTO.toEntity(o, demand)).toList();

        demand.setObservations(observations);

        return demand;
    }

}
