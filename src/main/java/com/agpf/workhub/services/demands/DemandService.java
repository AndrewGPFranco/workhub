package com.agpf.workhub.services.demands;

import com.agpf.workhub.dtos.demands.EditDemandDTO;
import com.agpf.workhub.dtos.demands.OutputDemandDTO;
import com.agpf.workhub.dtos.demands.RegisterDemandDTO;
import com.agpf.workhub.dtos.http.PageResponseDTO;
import com.agpf.workhub.enums.demands.StatusDemandType;
import com.agpf.workhub.exceptions.BusinessException;
import com.agpf.workhub.exceptions.NotFoundException;
import com.agpf.workhub.models.demands.Demand;
import com.agpf.workhub.repositories.auth.UserRepository;
import com.agpf.workhub.repositories.demands.DemandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DemandService {

    private final UserRepository userRepository;
    private final DemandRepository demandRepository;
    private static final String USER_NOT_FOUND = "Usuário não encontrado!";

    @Transactional
    public String createDemand(RegisterDemandDTO dto, String email) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));

        var demand = dto.toEntity(user);

        var saved = demandRepository.save(demand);

        return String.format("Demanda: '%s' foi registrada com sucesso!", saved.getTitle());
    }

    public PageResponseDTO<OutputDemandDTO> getByUser(int page, String email, StatusDemandType status) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));

        var demands = status == null ?
                demandRepository.findByUserIdOrderByCreatedAtDesc(user.getId(), PageRequest.of(page, 5)) :
                demandRepository.findByUserIdAndStatusOrderByCreatedAtDesc(user.getId(), status, PageRequest.of(page, 5));

        return PageResponseDTO.fromPage(demands.map(OutputDemandDTO::fromEntity));
    }

    @Transactional
    public void editDemand(UUID idDemand, EditDemandDTO dto, String email) {
        var demand = demandRepository.findById(idDemand).orElseThrow(() -> new NotFoundException("Demanda não encontrada!"));

        var user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));

        if (!demand.getUser().equals(user))
            throw new BusinessException(String.format("A demanda com título: %s informada não pertence ao usuário: %s", demand.getTitle(), user.getUsername()));

        demand.setTitle(dto.title());
        demand.setDescription(dto.description());
        demand.setDeadline(dto.deadline());
        demand.setStatus(dto.status());
        demand.setObservationsToReview(dto.observationsToReview());
        demand.setPriority(dto.priority());

        demandRepository.save(demand);
    }

    @Transactional
    public void deleteDemand(UUID idDemand, String email) {
        Demand demand = demandRepository.findById(idDemand).orElseThrow(() -> new NotFoundException("Demanda não encontrada!"));

        var user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));

        if (!demand.getUser().equals(user))
            throw new BusinessException(String.format("A demanda com título: %s informada não pertence ao usuário: %s", demand.getTitle(), user.getUsername()));

        demandRepository.deleteById(idDemand);
    }

}
