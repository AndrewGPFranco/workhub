package com.agpf.workhub.services.user;

import com.agpf.workhub.dtos.user.daily.OutputDailyDTO;
import com.agpf.workhub.dtos.user.daily.RegisterDailyDTO;
import com.agpf.workhub.repositories.user.DailyRepository;
import com.agpf.workhub.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DailyService {

    private final UserRepository userRepository;
    private final DailyRepository dailyRepository;
    private static final String USER_NOT_FOUND = "Usuário não encontrado!";

    public String registerDaily(RegisterDailyDTO dto, String email) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));

        var entity = RegisterDailyDTO.toEntity(dto, user);

        dailyRepository.save(entity);

        return "Registro para a daily criada com sucesso!";
    }

    public OutputDailyDTO getByUser(LocalDate date, String email) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));

        var daily = dailyRepository.findByUserAndDateSummary(date, user.getId());

        if (daily == null)
            return null;

        return OutputDailyDTO.fromEntity(daily);
    }
}
