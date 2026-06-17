package com.agpf.workhub.dtos.user.feedback;

import com.agpf.workhub.models.user.Feedback;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

public record OutputFeedbackDTO(
        UUID id,
        LocalDate date,
        Month month,
        String peopleFeedback,
        String summary,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        UUID subdomainId
) {
    public static OutputFeedbackDTO fromEntity(Feedback feedback) {
        return new OutputFeedbackDTO(
                feedback.getId(), feedback.getDate(), feedback.getMonth(), feedback.getPeopleFeedback(),
                feedback.getSummary(), feedback.getCreatedAt(), feedback.getUpdatedAt(),
                feedback.getSubdomain() == null ? null : feedback.getSubdomain().getId()
        );
    }
}
