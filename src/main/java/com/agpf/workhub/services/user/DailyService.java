package com.agpf.workhub.services.user;

import com.agpf.workhub.dtos.user.daily.OutputDailyDTO;
import com.agpf.workhub.dtos.user.daily.RegisterDailyDTO;
import com.agpf.workhub.models.user.User;
import com.agpf.workhub.repositories.user.DailyRepository;
import com.agpf.workhub.services.subdomains.SubdomainAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DailyService {

    private final DailyRepository dailyRepository;
    private final SubdomainAccessService subdomainAccessService;

    public String registerDaily(RegisterDailyDTO dto, User user) {
        var subdomain = subdomainAccessService.resolve(user, dto.subdomainId());
        var entity = RegisterDailyDTO.toEntity(dto, user, subdomain);

        dailyRepository.save(entity);

        return "Registro para a daily criada com sucesso!";
    }

    public List<OutputDailyDTO> getByUser(LocalDate startDate, LocalDate endDate, User user, UUID subdomainId) {
        var subdomain = subdomainAccessService.resolve(user, subdomainId);
        var daily = dailyRepository.findByUserBetweenDates(
                startDate, endDate, user.getId(), subdomain == null ? null : subdomain.getId()
        );

        if (daily == null)
            return new ArrayList<>();

        return daily.stream().map(OutputDailyDTO::fromEntity).toList();
    }
}
