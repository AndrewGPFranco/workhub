package com.agpf.workhub.services.user;

import com.agpf.workhub.dtos.user.daily.OutputDailyDTO;
import com.agpf.workhub.dtos.user.daily.RegisterDailyDTO;
import com.agpf.workhub.models.user.User;
import com.agpf.workhub.repositories.user.DailyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyService {

    private final DailyRepository dailyRepository;

    public String registerDaily(RegisterDailyDTO dto, User user) {
        var entity = RegisterDailyDTO.toEntity(dto, user);

        dailyRepository.save(entity);

        return "Registro para a daily criada com sucesso!";
    }

    public List<OutputDailyDTO> getByUser(LocalDate startDate, LocalDate endDate, User user) {
        var daily = dailyRepository.findByUserBetweenDates(startDate, endDate, user.getId());

        if (daily == null)
            return new ArrayList<>();

        return daily.stream().map(OutputDailyDTO::fromEntity).toList();
    }
}
