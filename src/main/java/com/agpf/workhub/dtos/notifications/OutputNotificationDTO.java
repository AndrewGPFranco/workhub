package com.agpf.workhub.dtos.notifications;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record OutputNotificationDTO(
        @NotBlank Long id,
        @JsonProperty("content") @NotBlank String content,
        @JsonProperty("created_at") @NotNull LocalDate createdAt,
        @JsonProperty("was_it_viewed") boolean wasItViewed
) {
}
