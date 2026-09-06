package com.agpf.workhub.services.sprints;

import com.agpf.workhub.dtos.sprints.InputSprintDTO;
import com.agpf.workhub.models.sprint.Sprint;
import com.agpf.workhub.models.user.User;
import com.agpf.workhub.repositories.sprint.SprintRepository;
import com.agpf.workhub.services.subdomains.SubdomainAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SprintService {

    private final SprintRepository sprintRepository;
    private final SubdomainAccessService subdomainAccessService;

    public List<String> getSprintsByUserAndSubdomain(User user, UUID idSubdomain) {
        return sprintRepository.getByUserAndSubdomain(user.getId(), idSubdomain);
    }

    @Transactional
    public String register(User user, InputSprintDTO dto) {
        var subdomain = subdomainAccessService.resolve(user, dto.idSubdomain());

        var sprint = Sprint.builder()
                .title(dto.title()).dateToUse(dto.dateToUse()).subdomain(subdomain).user(user).build();

        sprintRepository.save(sprint);

        return "Sprint salva!";
    }
}
