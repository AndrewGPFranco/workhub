package com.agpf.workhub.services.user;

import com.agpf.workhub.dtos.http.PageResponseDTO;
import com.agpf.workhub.dtos.user.feedback.OutputFeedbackDTO;
import com.agpf.workhub.dtos.user.feedback.RegisterFeedbackDTO;
import com.agpf.workhub.repositories.user.FeedbackRepository;
import com.agpf.workhub.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final UserRepository userRepository;
    private final FeedbackRepository feedbackRepository;
    private static final String USER_NOT_FOUND = "Usuário não encontrado!";

    public String registerFeedback(RegisterFeedbackDTO dto, String email) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));

        var entity = RegisterFeedbackDTO.toEntity(dto, user);

        feedbackRepository.save(entity);

        return "Registro do feedback salvo com sucesso!";
    }

    public PageResponseDTO<OutputFeedbackDTO> getByUser(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));

        var feedback = feedbackRepository.findByUser(user, PageRequest.of(0, 50));

        if (feedback == null)
            return null;

        return PageResponseDTO.fromPage(feedback.map(OutputFeedbackDTO::fromEntity));
    }
}
