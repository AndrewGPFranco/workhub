package com.agpf.workhub.services.sprints;

import com.agpf.workhub.dtos.sprints.InputDemandsToSprintDTO;
import com.agpf.workhub.dtos.sprints.InputSprintDTO;
import com.agpf.workhub.exceptions.NotFoundException;
import com.agpf.workhub.models.sprint.Sprint;
import com.agpf.workhub.models.user.User;
import com.agpf.workhub.repositories.demands.DemandRepository;
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
    private final DemandRepository demandRepository;
    private final SubdomainAccessService subdomainAccessService;

    public List<String> getSprintsByUserAndSubdomain(User user, UUID idSubdomain) {
        if (idSubdomain == null)
            return sprintRepository.getByUserAndSubdomainNull(user.getId());

        return sprintRepository.getByUserAndSubdomainNull(user.getId(), idSubdomain);
    }

    @Transactional
    public String register(User user, InputSprintDTO dto) {
        var subdomain = subdomainAccessService.resolve(user, dto.idSubdomain());

        var sprint = Sprint.builder()
                .title(dto.title()).dateToUse(dto.dateToUse()).subdomain(subdomain).user(user).build();

        sprintRepository.save(sprint);

        return "Sprint salva!";
    }

    @Transactional
    public String addDemandsToSprint(User user, InputDemandsToSprintDTO dto) {
        var subdomain = subdomainAccessService.resolve(user, dto.idSubdomain());

        var sprint = sprintRepository.findByTitleAndUser(dto.sprintTitle(), user)
                .orElseThrow(() -> new NotFoundException("Sprint não encontrada!"));

        if (subdomain == null)
            demandRepository.addDemandsToSprint(sprint, dto.idDemands());
        else
            demandRepository.addDemandsToSprint(sprint, dto.idDemands(), subdomain.getId());

        return String.format("Demandas enviadas a Sprint: %s", dto.sprintTitle());
    }
}
