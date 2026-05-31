package com.agpf.workhub.dtos.demands;

import com.agpf.workhub.enums.demands.PriorityDemandType;
import com.agpf.workhub.enums.demands.StatusDemandType;
import com.agpf.workhub.models.auth.User;
import com.agpf.workhub.models.demands.Demand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RegisterDemandDTO(
        @NotBlank String title,
        @NotBlank String description,
        LocalDate deadline,
        @NotNull StatusDemandType status,
        @NotNull PriorityDemandType priority
) {

    public Demand toEntity(User user) {
        return Demand.builder().user(user).title(title)
                .description(description).deadline(deadline).status(status).priority(priority).build();
    }

}
