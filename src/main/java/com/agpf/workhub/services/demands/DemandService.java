package com.agpf.workhub.services.demands;

import com.agpf.workhub.dtos.demands.EditDemandDTO;
import com.agpf.workhub.dtos.demands.OutputDemandDTO;
import com.agpf.workhub.dtos.demands.RegisterDemandDTO;
import com.agpf.workhub.exceptions.NotFoundException;
import com.agpf.workhub.models.demands.Demand;
import com.agpf.workhub.repositories.auth.UserRepository;
import com.agpf.workhub.repositories.demands.DemandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DemandService {

    private final UserRepository userRepository;
    private final DemandRepository demandRepository;

    @Transactional
    public String createDemand(RegisterDemandDTO dto, String email) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));

        var demand = dto.toEntity(user);

        var saved = demandRepository.save(demand);

        return String.format("Demanda: '%s' foi registrada com sucesso!", saved.getTitle());
    }

    public List<OutputDemandDTO> getByUser(int page, String email) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));

        var demands = demandRepository.getDemandsByUser(user.getId(), PageRequest.of(page, 10));

        return demands.stream().map(OutputDemandDTO::fromEntity).toList();
    }

    @Transactional
    public String editDemand(UUID idDemand, EditDemandDTO dto, String email) {
        var demand = demandRepository.findById(idDemand).orElseThrow(() -> new NotFoundException("Demanda não encontrada!"));

        demand.setTitle(dto.title());
        demand.setDescription(dto.description());
        demand.setDeadline(dto.deadline());
        demand.setStatus(dto.status());
        demand.setObservationsToReview(dto.observationsToReview());
        demand.setPriority(dto.priority());

        demandRepository.save(demand);
        return "Demanda editada com sucesso!";
    }
}
