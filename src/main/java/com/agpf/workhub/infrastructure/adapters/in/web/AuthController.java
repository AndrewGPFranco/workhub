package com.agpf.workhub.infrastructure.adapters.in.web;

import com.agpf.workhub.domain.ports.in.AuthUseCase;
import com.agpf.workhub.infrastructure.dto.auth.AuthLoginDTO;
import com.agpf.workhub.infrastructure.dto.auth.ResponseAPI;
import com.agpf.workhub.infrastructure.dto.auth.UserRegisterDTO;
import com.agpf.workhub.infrastructure.dto.auth.ValidatorAccountDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;

    @PostMapping(value = "/auth/register")
    public ResponseEntity<ResponseAPI<String>> registrarUsuario(@Valid @RequestBody UserRegisterDTO userDTO) {
        authUseCase.registrarUsuario(userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseAPI<>("Um email com o código de validação foi enviado!"));
    }

    @PostMapping(value = "/auth/register/code")
    public ResponseEntity<ResponseAPI<String>> validarConta(@Valid @RequestBody ValidatorAccountDTO dto) {
        authUseCase.validarConta(dto);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseAPI<>("A conta foi validada com sucesso!"));
    }

    @PostMapping(value = "/auth/register/code")
    public ResponseEntity<ResponseAPI<String>> realizarLogin(@Valid @RequestBody AuthLoginDTO dto) {
        String token = authUseCase.realizarLogin(dto);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseAPI<>(token));
    }


}
