package com.agpf.workhub.services.subdomains;

import com.agpf.workhub.exceptions.NotFoundException;
import com.agpf.workhub.models.subdomains.Subdomain;
import com.agpf.workhub.models.user.User;
import com.agpf.workhub.repositories.subdomains.SubdomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubdomainAccessService {

    private final SubdomainRepository subdomainRepository;

    public Subdomain resolve(User user, UUID subdomainId) {
        if (subdomainId == null)
            return null;

        return subdomainRepository.findByIdAndUser(subdomainId, user)
                .orElseThrow(() -> new NotFoundException("Subdomínio não encontrado!"));
    }
}
