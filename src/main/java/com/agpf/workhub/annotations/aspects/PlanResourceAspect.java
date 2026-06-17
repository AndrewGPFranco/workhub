package com.agpf.workhub.annotations.aspects;

import com.agpf.workhub.annotations.PlanResource;
import com.agpf.workhub.exceptions.BusinessException;
import com.agpf.workhub.models.user.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@Component
public class PlanResourceAspect {

    @Before("@annotation(com.agpf.workhub.annotations.PlanResource) || @within(com.agpf.workhub.annotations.PlanResource)")
    public void validate(JoinPoint joinPoint) {
        PlanResource planResource = getPlanResource(joinPoint);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null)
            throw new BusinessException("Ocorreu um problema ao processar a solicitação!");

        if (!(authentication.getPrincipal() instanceof User user))
            throw new BusinessException("Ocorreu um problema ao processar a solicitação!");

        var allowed = user.getContractedResources().stream().anyMatch(r -> r.equals(planResource.verify()));

        if (!allowed)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Recurso não disponível para o plano do usuário.");
    }

    private PlanResource getPlanResource(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        PlanResource methodAnnotation = signature.getMethod().getAnnotation(PlanResource.class);

        if (methodAnnotation != null)
            return methodAnnotation;

        return joinPoint.getTarget().getClass().getAnnotation(PlanResource.class);
    }

}
