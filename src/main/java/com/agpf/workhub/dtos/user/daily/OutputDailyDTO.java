package com.agpf.workhub.dtos.user.daily;

import com.agpf.workhub.models.user.Daily;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record OutputDailyDTO(
        UUID id,
        LocalDate date,
        String summary,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static OutputDailyDTO fromEntity(Daily daily) {
        return new OutputDailyDTO(daily.getId(), daily.getDate(), daily.getSummary(), daily.getCreatedAt(), daily.getUpdatedAt());
    }
}
