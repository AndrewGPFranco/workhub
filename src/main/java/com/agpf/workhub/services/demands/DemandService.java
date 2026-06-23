package com.agpf.workhub.services.demands;

import com.agpf.workhub.dtos.demands.EditDemandDTO;
import com.agpf.workhub.dtos.demands.InputObservationDTO;
import com.agpf.workhub.dtos.demands.OutputDemandDTO;
import com.agpf.workhub.dtos.demands.RegisterDemandDTO;
import com.agpf.workhub.dtos.http.PageResponseDTO;
import com.agpf.workhub.enums.demands.PriorityDemandType;
import com.agpf.workhub.enums.demands.StatusDemandType;
import com.agpf.workhub.exceptions.BusinessException;
import com.agpf.workhub.exceptions.NotFoundException;
import com.agpf.workhub.models.user.User;
import com.agpf.workhub.repositories.demands.DemandRepository;
import com.agpf.workhub.repositories.demands.ObservationRepository;
import com.agpf.workhub.services.subdomains.SubdomainAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DemandService {

    private final DemandRepository demandRepository;
    private final ObservationRepository observationRepository;
    private final SubdomainAccessService subdomainAccessService;

    private static final String DEMAND_NOT_FOUND = "Demanda não encontrada!";

    @Transactional
    public String createDemand(RegisterDemandDTO dto, User user) {
        var subdomain = subdomainAccessService.resolve(user, dto.subdomainId());

        var demand = dto.toEntity(user, subdomain);

        var saved = demandRepository.save(demand);

        return String.format("Demanda: '%s' foi registrada com sucesso!", saved.getTitle());
    }

    public PageResponseDTO<OutputDemandDTO> getByUser(int page, User user, StatusDemandType status,
                                                      PriorityDemandType priority, UUID subdomainId) {
        var subdomain = subdomainAccessService.resolve(user, subdomainId);
        var demands = demandRepository.findByUserAndSubdomainAndFilters(
                user.getId(),
                subdomain == null ? null : subdomain.getId(),
                status,
                priority,
                PageRequest.of(page, 5)
        );

        return PageResponseDTO.fromPage(demands.map(OutputDemandDTO::fromEntity));
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

        if (dto.finalizedAt() != null) {
            demand.setStatus(StatusDemandType.DONE);
            demand.setFinalizedAt(dto.finalizedAt());
        }

        if (StatusDemandType.DONE.equals(dto.status()) && dto.finalizedAt() == null)
            demand.setFinalizedAt(LocalDate.now());

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
}
