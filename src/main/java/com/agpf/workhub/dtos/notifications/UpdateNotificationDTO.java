package com.agpf.workhub.dtos.notifications;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record UpdateNotificationDTO(
        @JsonProperty("user_id") @NotNull Long idUser,
        @JsonProperty("notification_id") @NotNull Long idNotification
) {
}
