package com.agpf.workhub.dtos.user;

import com.agpf.workhub.models.user.Daily;
import com.agpf.workhub.models.user.User;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RegisterDailyDTO(
        @NotNull(message = "É necessário informar a data do feedback!") LocalDate date,
        @NotNull(message = "É necessário informar a pessoa que aplicou o feedback!") String summary
) {

    public static Daily toEntity(RegisterDailyDTO dto, User user) {
        return Daily.builder().date(dto.date).summary(dto.summary)
                .user(user).createdAt(LocalDateTime.now()).updatedAt(null).build();
    }

}
