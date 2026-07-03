package com.agpf.workhub.services.subdomains;

import com.agpf.workhub.dtos.subdomains.EditSubdomainDTO;
import com.agpf.workhub.dtos.subdomains.OutputSubdomain;
import com.agpf.workhub.dtos.subdomains.RegisterSubdomainDTO;
import com.agpf.workhub.exceptions.BusinessException;
import com.agpf.workhub.models.subdomains.Subdomain;
import com.agpf.workhub.models.user.User;
import com.agpf.workhub.repositories.subdomains.SubdomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.agpf.workhub.utils.UtilsService.updateField;

@Service
@RequiredArgsConstructor
public class SubdomainService {

    private final SubdomainRepository subdomainRepository;
    private final SubdomainAccessService subdomainAccessService;

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

    @Transactional
    public void edit(EditSubdomainDTO dto, UUID idSubdomain, User user) {
        var subdomain = subdomainAccessService.resolve(user, idSubdomain);

        updateField(dto.name(), subdomain::setName);
        updateField(dto.urlPhoto(), subdomain::setUrlPhoto);

        subdomainRepository.save(subdomain);
    }
}
