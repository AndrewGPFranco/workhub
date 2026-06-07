package com.agpf.workhub.dtos.user.feedback;

import com.agpf.workhub.models.user.Feedback;
import com.agpf.workhub.models.user.User;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RegisterFeedbackDTO(
        @NotNull(message = "É necessário informar a data do feedback!") LocalDate date,
        @NotNull(message = "É necessário informar a pessoa que aplicou o feedback!") String peopleFeedback
) {

    public static Feedback toEntity(RegisterFeedbackDTO dto, User user) {
        return Feedback.builder()
                .peopleFeedback(dto.peopleFeedback).createdAt(LocalDateTime.now())
                .updatedAt(null).date(dto.date).user(user).month(dto.date.getMonth()).build();
    }

}
