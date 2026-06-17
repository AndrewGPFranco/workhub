package com.agpf.workhub.services.user;

import com.agpf.workhub.dtos.http.PageResponseDTO;
import com.agpf.workhub.dtos.user.feedback.OutputFeedbackDTO;
import com.agpf.workhub.dtos.user.feedback.RegisterFeedbackDTO;
import com.agpf.workhub.models.user.User;
import com.agpf.workhub.repositories.user.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public String registerFeedback(RegisterFeedbackDTO dto, User user) {
        var entity = RegisterFeedbackDTO.toEntity(dto, user);

        feedbackRepository.save(entity);

        return "Registro do feedback salvo com sucesso!";
    }

    public PageResponseDTO<OutputFeedbackDTO> getByUser(User user) {
        var feedback = feedbackRepository.findByUser(user, PageRequest.of(0, 50));

        if (feedback == null)
            return null;

        return PageResponseDTO.fromPage(feedback.map(OutputFeedbackDTO::fromEntity));
    }
}
