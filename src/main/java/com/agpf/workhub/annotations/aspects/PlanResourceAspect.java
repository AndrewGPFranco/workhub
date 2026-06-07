package com.agpf.workhub.annotations.aspects;

import com.agpf.workhub.annotations.PlanResource;
import com.agpf.workhub.exceptions.BusinessException;
import com.agpf.workhub.exceptions.NotFoundException;
import com.agpf.workhub.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@Component
@RequiredArgsConstructor
public class PlanResourceAspect {

    private final UserRepository userRepository;

    @Before("@annotation(planResource)")
    public void validate(PlanResource planResource) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            var user = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado!"));

            var allowed = user.getContractedResources().stream().anyMatch(r -> r.equals(planResource.verify()));

            if (!allowed)
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Recurso não disponível para o plano do usuário.");
        }

        throw new BusinessException("Ocorreu um problema ao processar a solicitação!");
    }

}
