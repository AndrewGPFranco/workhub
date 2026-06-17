package com.agpf.workhub.dtos.user.daily;

import com.agpf.workhub.models.subdomains.Subdomain;
import com.agpf.workhub.models.user.Daily;
import com.agpf.workhub.models.user.User;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record RegisterDailyDTO(
        @NotNull(message = "É necessário informar a data!") LocalDate date,
        @NotNull(message = "É necessário informar o resumo!") String summary,
        UUID subdomainId
) {

    public static Daily toEntity(RegisterDailyDTO dto, User user, Subdomain subdomain) {
        return Daily.builder().date(dto.date).summary(dto.summary)
                .user(user).subdomain(subdomain).createdAt(LocalDateTime.now()).updatedAt(null).build();
    }

}
