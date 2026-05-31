package com.agpf.workhub.services.demands;

import com.agpf.workhub.dtos.demands.RegisterDemandDTO;
import com.agpf.workhub.models.auth.User;
import com.agpf.workhub.repositories.demands.DemandRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DemandService {

    private final DemandRepository demandRepository;

    public DemandService(DemandRepository demandRepository) {
        this.demandRepository = demandRepository;
    }

    @Transactional
    public String createDemand(RegisterDemandDTO dto, User user) {
        var demand = dto.toEntity(user);

        var saved = demandRepository.save(demand);

        return String.format("Demanda: %s foi registrada com sucesso!", saved.getTitle());
    }

}
