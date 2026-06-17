package com.agpf.workhub.dtos.user.feedback;

import com.agpf.workhub.models.subdomains.Subdomain;
import com.agpf.workhub.models.user.Feedback;
import com.agpf.workhub.models.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record RegisterFeedbackDTO(
        @NotNull(message = "É necessário informar a data do feedback!") LocalDate date,
        @NotBlank(message = "É necessário informar algo no feedback!") String feedback,
        @NotBlank(message = "É necessário informar a pessoa que aplicou o feedback!") String peopleFeedback,
        UUID subdomainId
) {

    public static Feedback toEntity(RegisterFeedbackDTO dto, User user, Subdomain subdomain) {
        return Feedback.builder().summary(dto.feedback)
                .peopleFeedback(dto.peopleFeedback).createdAt(LocalDateTime.now())
                .updatedAt(null).date(dto.date).user(user).month(dto.date.getMonth()).subdomain(subdomain).build();
    }

}
