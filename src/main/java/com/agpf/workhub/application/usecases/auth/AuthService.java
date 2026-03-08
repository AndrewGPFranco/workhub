package com.agpf.workhub.application.usecases.auth;

import com.agpf.workhub.application.usecases.CustomUserDetailsService;
import com.agpf.workhub.application.usecases.EmailService;
import com.agpf.workhub.domain.model.RoleType;
import com.agpf.workhub.domain.model.User;
import com.agpf.workhub.domain.ports.in.AuthUseCase;
import com.agpf.workhub.domain.ports.out.UserRepositoryPort;
import com.agpf.workhub.infrastructure.dto.auth.AuthLoginDTO;
import com.agpf.workhub.infrastructure.dto.auth.UserCacheDTO;
import com.agpf.workhub.infrastructure.dto.auth.UserRegisterDTO;
import com.agpf.workhub.infrastructure.dto.auth.ValidatorAccountDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

    private final Jedis jedis;
    private ObjectMapper objectMapper;
    private final PasswordEncoder encoder;
    private final EmailService emailService;
    private final JwtTokenService jwtTokenService;
    private final UserRepositoryPort userRepository;

    private static final SecureRandom random = new SecureRandom();

    @PostConstruct
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    @Transactional
    public void registrarUsuario(UserRegisterDTO dto) {
        User usuario = User.builder()
                .email(dto.email())
                .fullName(dto.fullname())
                .username(dto.username())
                .firstName(dto.firstName())
                .dateBirth(dto.dateBirth())
                .roles(Set.of(RoleType.USER))
                .createdAt(LocalDateTime.now())
                .password(encoder.encode(dto.password()))
                .build();

        String codigo = gerarCodigo();
        UserCacheDTO usuarioCache = new UserCacheDTO(usuario, codigo);

        String json;

        try {
            json = objectMapper.writeValueAsString(usuarioCache);
        } catch (JsonProcessingException jpe) {
            log.error("Erro ao gerar json para cachear o usuário! \n", jpe.getMessage());
            throw new RuntimeException("Ocorreu um erro ao realizar o registro!");
        }

        jedis.set(usuario.getEmail(), json);

        emailService.enviarEmailComCodigoParaRegistro("andrewgomes1328@gmail.com", codigo);
    }

    public static String gerarCodigo() {
        int codigo = 100000 + random.nextInt(900000);
        return String.valueOf(codigo);
    }

    @Override
    public void validarConta(ValidatorAccountDTO dto) {
        String json = jedis.get(dto.email());

        UserCacheDTO userCache;
        try {
            userCache = objectMapper.readValue(json, UserCacheDTO.class);
        } catch (JsonProcessingException jpe) {
            log.error("Erro ao desserealizar json contendo o usuário! \n", jpe.getMessage());
            throw new RuntimeException("Ocorreu um erro ao validar a conta!");
        }

        if (!userCache.code().equals(dto.code())) {
            throw new RuntimeException("Código de validação inválido!");
        }

        userRepository.save(userCache.user());
        jedis.del(dto.email());
    }

    @Override
    public String realizarLogin(AuthLoginDTO dto) {
        boolean isEmail = CustomUserDetailsService.isEmail(dto.usernameOrEmail());

        Optional<User> usuario;
        if (isEmail)
            usuario = userRepository.findByEmail(dto.usernameOrEmail());
        else
            usuario = userRepository.findByUsername(dto.usernameOrEmail());

        if (usuario.isEmpty())
            throw new RuntimeException("Usuário informado não foi encontrado!");

        if (!encoder.matches(dto.password(), usuario.get().getPassword()))
            throw new RuntimeException("A senha informada está incorreta!");

        return jwtTokenService.generateToken(usuario.get());
    }

}
