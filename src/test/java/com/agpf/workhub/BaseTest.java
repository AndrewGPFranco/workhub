package com.agpf.workhub;

import com.agpf.workhub.enums.demands.PriorityDemandType;
import com.agpf.workhub.enums.demands.StatusDemandType;
import com.agpf.workhub.models.auth.User;
import com.agpf.workhub.models.demands.Demand;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Classe base para centralizar métodos e afins comuns a todos os testes.
 */
public abstract class BaseTest {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public BaseTest() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public User getUser() {
        return new User(1L, "email@gmail.com",
                "username", bCryptPasswordEncoder.encode("minhasenha"),
                "ADMIN", "Silva", "João", LocalDateTime.now(), null);
    }

    public Demand getDemand() {
        return new Demand(UUID.randomUUID(), getUser(), "Demanda importante", "Programar utilizando TDD", null,
                StatusDemandType.ONGOING, LocalDateTime.now(), null, PriorityDemandType.LOW);
    }

}
