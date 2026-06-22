package com.agpf.workhub.dtos.demands;

import com.agpf.workhub.models.demands.Demand;
import com.agpf.workhub.models.demands.Observation;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record InputObservationDTO(
        @NotNull UUID demandId,
        @NotEmpty List<String> textObservations
) {

    public static Observation toEntity(String observation, Demand demand) {
        return Observation.builder()
                .createdAt(Instant.now()).updatedAt(null).demand(demand).textObservation(observation).build();
    }

}
