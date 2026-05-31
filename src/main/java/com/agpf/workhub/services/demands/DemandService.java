package com.agpf.workhub.services.demands;

import com.agpf.workhub.dtos.demands.RegisterDemandDTO;
import com.agpf.workhub.repositories.auth.UserRepository;
import com.agpf.workhub.repositories.demands.DemandRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DemandService {

    private final UserRepository userRepository;
    private final DemandRepository demandRepository;

    public DemandService(DemandRepository demandRepository, UserRepository userRepository) {
        this.demandRepository = demandRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public String createDemand(RegisterDemandDTO dto, String email) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));

        var demand = dto.toEntity(user);

        var saved = demandRepository.save(demand);

        return String.format("Demanda: '%s' foi registrada com sucesso!", saved.getTitle());
    }

}
