package com.agpf.workhub.domain.ports.in;

import com.agpf.workhub.infrastructure.dto.auth.AuthLoginDTO;
import com.agpf.workhub.infrastructure.dto.auth.UserRegisterDTO;
import com.agpf.workhub.infrastructure.dto.auth.ValidatorAccountDTO;
import jakarta.validation.Valid;

public interface AuthUseCase {

    void registrarUsuario(@Valid UserRegisterDTO dto);

    void validarConta(@Valid ValidatorAccountDTO dto);

    String realizarLogin(@Valid AuthLoginDTO dto);

}
