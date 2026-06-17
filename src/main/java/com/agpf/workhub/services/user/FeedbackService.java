package com.agpf.workhub.services.user;

import com.agpf.workhub.dtos.http.PageResponseDTO;
import com.agpf.workhub.dtos.user.feedback.OutputFeedbackDTO;
import com.agpf.workhub.dtos.user.feedback.RegisterFeedbackDTO;
import com.agpf.workhub.models.user.User;
import com.agpf.workhub.repositories.user.FeedbackRepository;
import com.agpf.workhub.services.subdomains.SubdomainAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final SubdomainAccessService subdomainAccessService;

    public String registerFeedback(RegisterFeedbackDTO dto, User user) {
        var subdomain = subdomainAccessService.resolve(user, dto.subdomainId());
        var entity = RegisterFeedbackDTO.toEntity(dto, user, subdomain);

        feedbackRepository.save(entity);

        return "Registro do feedback salvo com sucesso!";
    }

    public PageResponseDTO<OutputFeedbackDTO> getByUser(User user, UUID subdomainId) {
        var subdomain = subdomainAccessService.resolve(user, subdomainId);
        var feedback = feedbackRepository.findByUserAndSubdomain(
                user, subdomain == null ? null : subdomain.getId(), PageRequest.of(0, 50)
        );

        if (feedback == null)
            return null;

        return PageResponseDTO.fromPage(feedback.map(OutputFeedbackDTO::fromEntity));
    }
}
