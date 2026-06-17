package com.agpf.workhub.services.subdomains;

import com.agpf.workhub.dtos.subdomains.OutputSubdomain;
import com.agpf.workhub.dtos.subdomains.RegisterSubdomainDTO;
import com.agpf.workhub.exceptions.BusinessException;
import com.agpf.workhub.models.user.User;
import com.agpf.workhub.repositories.subdomains.SubdomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubdomainService {

    private final SubdomainRepository subdomainRepository;

    @Transactional
    public void register(RegisterSubdomainDTO dto, User user) {
        var subdomainOptional = subdomainRepository.findByNameAndUser(dto.name(), user);

        if (subdomainOptional.isPresent())
            throw new BusinessException("Já há um subdomínio com o nome informado!");

        var entity = RegisterSubdomainDTO.toEntity(dto, user);
        subdomainRepository.save(entity);
    }

    public List<OutputSubdomain> subdomainsByUser(User user) {
        return subdomainRepository.subdomainsByUser(user.getId());
    }
}
