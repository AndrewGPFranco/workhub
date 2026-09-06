package com.agpf.workhub.services.demands;

import com.agpf.workhub.dtos.demands.*;
import com.agpf.workhub.dtos.http.PageResponseDTO;
import com.agpf.workhub.enums.demands.PriorityDemandType;
import com.agpf.workhub.enums.demands.StatusDemandType;
import com.agpf.workhub.exceptions.BusinessException;
import com.agpf.workhub.exceptions.NotFoundException;
import com.agpf.workhub.models.sprint.Sprint;
import com.agpf.workhub.models.user.User;
import com.agpf.workhub.repositories.demands.DemandRepository;
import com.agpf.workhub.repositories.demands.ObservationRepository;
import com.agpf.workhub.repositories.sprint.SprintRepository;
import com.agpf.workhub.repositories.subdomains.SubdomainRepository;
import com.agpf.workhub.services.subdomains.SubdomainAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.agpf.workhub.utils.DateUtils.getLocalDateAmericaSP;

@Service
@RequiredArgsConstructor
public class DemandService {

    private final DemandRepository demandRepository;
    private final SprintRepository sprintRepository;
    private final SubdomainRepository subdomainRepository;
    private final ObservationRepository observationRepository;
    private final SubdomainAccessService subdomainAccessService;

    private static final String DEMAND_NOT_FOUND = "Demanda não encontrada!";

    @Transactional
    public String createDemand(RegisterDemandDTO dto, User user) {
        var subdomain = subdomainAccessService.resolve(user, dto.subdomainId());

        var optionalSprint = getSprintByTitle(dto.sprint()).orElse(null);

        var demand = dto.toEntity(user, subdomain, optionalSprint);

        var saved = demandRepository.save(demand);

        return String.format("Demanda: '%s' foi registrada com sucesso!", saved.getTitle());
    }

    private Optional<Sprint> getSprintByTitle(String sprintTitle) {
        return sprintRepository.findByTitle(sprintTitle);
    }

    public List<PageResponseDTO<OutputDemandDTO>> getByUser(int page, User user, StatusDemandType status,
                                                            PriorityDemandType priority, String sprint, UUID subdomainId) {
        var subdomain = subdomainAccessService.resolve(user, subdomainId);

        var demandList = new ArrayList<PageResponseDTO<OutputDemandDTO>>();

        if (sprint != null) {
            var demands = demandRepository.buscarDemandsPorSprint(user.getId(), subdomain == null ? null : subdomain.getId(),
                    priority, sprint, PageRequest.of(page, 5));

            return List.of(PageResponseDTO.fromPage(demands.map(OutputDemandDTO::fromEntity)));
        }

        if (status != null) {
            var demands = demandRepository.findByUserAndSubdomainAndFilters(user.getId(),
                    subdomain == null ? null : subdomain.getId(), status, priority, PageRequest.of(page, 5));

            return List.of(PageResponseDTO.fromPage(demands.map(OutputDemandDTO::fromEntity)));
        }

        for (var currentStatus : StatusDemandType.values()) {
            var demands = demandRepository.findByUserAndSubdomainAndFilters(user.getId(),
                    subdomain == null ? null : subdomain.getId(), currentStatus, priority, PageRequest.of(page, 5));

            demandList.add(PageResponseDTO.fromPage(demands.map(OutputDemandDTO::fromEntity)));
        }

        return demandList;
    }

    @Transactional
    public void editDemand(UUID idDemand, EditDemandDTO dto, User user) {
        var demand = demandRepository.findById(idDemand).orElseThrow(() -> new NotFoundException(DEMAND_NOT_FOUND));

        if (!demand.getUser().getId().equals(user.getId())) {
            throw new BusinessException(
                    String.format("A demanda com título: %s informada não pertence ao usuário: %s",
                            demand.getTitle(), user.getUsername())
            );
        }

        demand.setTitle(dto.title());
        demand.setDescription(dto.description());
        demand.setDeadline(dto.deadline());
        demand.setStatus(dto.status());
        demand.setObservationsToReview(dto.observationsToReview());
        demand.setPriority(dto.priority());
        demand.setSubdomain(subdomainAccessService.resolve(user, dto.subdomainId()));
        demand.setSprint(getSprintByTitle(dto.sprint()).orElse(null));

        if (dto.finalizedAt() != null) {
            demand.setStatus(StatusDemandType.DONE);
            demand.setFinalizedAt(dto.finalizedAt());
        }

        if (StatusDemandType.DONE.equals(dto.status()) && dto.finalizedAt() == null)
            demand.setFinalizedAt(getLocalDateAmericaSP());

        demandRepository.save(demand);
    }

    @Transactional
    public void deleteDemand(UUID idDemand, User user) {
        var demand = demandRepository.findById(idDemand).orElseThrow(() -> new NotFoundException(DEMAND_NOT_FOUND));

        if (!demand.getUser().getId().equals(user.getId()))
            throw new BusinessException(String.format("A demanda com título: %s informada não pertence ao usuário: %s", demand.getTitle(), user.getUsername()));

        demandRepository.deleteById(idDemand);
    }

    public List<OutputDemandDTO> searchByDemand(String title, User user, UUID subdomainId) {
        var subdomain = subdomainAccessService.resolve(user, subdomainId);
        var demands = demandRepository.searchByDemand(title, user.getId(), subdomain == null ? null : subdomain.getId());

        return demands.stream().map(OutputDemandDTO::fromEntity).toList();
    }

    @Transactional
    public void addObservationsToDemand(InputObservationDTO dto) {
        var demand = demandRepository.findById(dto.demandId()).orElseThrow(
                () -> new NotFoundException(DEMAND_NOT_FOUND)
        );

        if (demand.getObservations() == null)
            demand.setObservations(new ArrayList<>());

        var observations = dto.textObservations().stream().map(t -> InputObservationDTO.toEntity(t, demand)).toList();

        var observationsPersisted = observationRepository.saveAll(observations);

        demand.getObservations().addAll(observationsPersisted);

        demandRepository.save(demand);
    }

    @Transactional
    public String changeDemandSubdomain(UUID idDemand, UUID idSubdomain, User user) {
        var demand = demandRepository.findById(idDemand).orElseThrow(() -> new NotFoundException(DEMAND_NOT_FOUND));

        var subdomain = subdomainRepository.findByIdAndUser(idSubdomain, user)
                .orElseThrow(() -> new NotFoundException("Subdomínio não encontrado!"));

        if (demand.getSubdomain() == subdomain)
            throw new BusinessException("A demanda já pertence ao subdomínio mencionado!");

        demand.setSubdomain(subdomain);

        demandRepository.save(demand);

        return String.format("A demanda foi passado ao subdomínio alvo: %s", subdomain.getName());
    }

    public List<OutputDemandCronDTO> obterTodasDemandas(String title) {
        return demandRepository.buscarTodasDemandasDaSprintAtual(title);
    }
}
