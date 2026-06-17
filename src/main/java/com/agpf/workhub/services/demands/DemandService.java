package com.agpf.workhub.services.demands;

import com.agpf.workhub.dtos.demands.EditDemandDTO;
import com.agpf.workhub.dtos.demands.OutputDemandDTO;
import com.agpf.workhub.dtos.demands.RegisterDemandDTO;
import com.agpf.workhub.dtos.http.PageResponseDTO;
import com.agpf.workhub.enums.demands.PriorityDemandType;
import com.agpf.workhub.enums.demands.StatusDemandType;
import com.agpf.workhub.exceptions.BusinessException;
import com.agpf.workhub.exceptions.NotFoundException;
import com.agpf.workhub.models.demands.Demand;
import com.agpf.workhub.models.user.User;
import com.agpf.workhub.repositories.demands.DemandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DemandService {

    private final DemandRepository demandRepository;

    @Transactional
    public String createDemand(RegisterDemandDTO dto, User user) {
        var demand = dto.toEntity(user);

        var saved = demandRepository.save(demand);

        return String.format("Demanda: '%s' foi registrada com sucesso!", saved.getTitle());
    }

    public PageResponseDTO<OutputDemandDTO> getByUser(int page, User user, StatusDemandType status, PriorityDemandType priority) {
        var demands = getDemandsFilter(user.getId(), status, PageRequest.of(page, 5), priority);

        return PageResponseDTO.fromPage(demands.map(OutputDemandDTO::fromEntity));
    }

    private Page<Demand> getDemandsFilter(Long userId, StatusDemandType status, Pageable pageable, PriorityDemandType priority) {
        if (status == null && priority == null)
            return demandRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
        else if (status != null && priority == null)
            return demandRepository.findByUserIdAndStatusOrderByCreatedAtDesc(userId, status, pageable);
        else if (status == null)
            return demandRepository.findByUserIdAndPriorityOrderByCreatedAtDesc(userId, priority, pageable);

        return demandRepository.findByUserIdAndPriorityAndStatusOrderByCreatedAtDesc(userId, priority, status, pageable);
    }

    @Transactional
    public void editDemand(UUID idDemand, EditDemandDTO dto, User user) {
        var demand = demandRepository.findById(idDemand).orElseThrow(() -> new NotFoundException("Demanda não encontrada!"));

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

        if (dto.finalizedAt() != null) {
            demand.setStatus(StatusDemandType.DONE);
            demand.setFinalizedAt(dto.finalizedAt());
        }

        demandRepository.save(demand);
    }

    @Transactional
    public void deleteDemand(UUID idDemand, User user) {
        Demand demand = demandRepository.findById(idDemand).orElseThrow(() -> new NotFoundException("Demanda não encontrada!"));

        if (!demand.getUser().getId().equals(user.getId()))
            throw new BusinessException(String.format("A demanda com título: %s informada não pertence ao usuário: %s", demand.getTitle(), user.getUsername()));

        demandRepository.deleteById(idDemand);
    }

    public List<OutputDemandDTO> searchByDemand(String title, User user) {
        var demands = demandRepository.searchByDemand(title, user.getId());

        return demands.stream().map(OutputDemandDTO::fromEntity).toList();
    }
}
