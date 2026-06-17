package com.agpf.workhub.services.auth;

import java.time.LocalDateTime;
import java.util.List;

import com.agpf.workhub.enums.plan.PlanResourceType;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.agpf.workhub.dtos.auth.AuthResponseDTO;
import com.agpf.workhub.dtos.auth.LoginRequestDTO;
import com.agpf.workhub.dtos.auth.RegisterRequestDTO;
import com.agpf.workhub.dtos.auth.UserResponseDTO;
import com.agpf.workhub.models.user.User;
import com.agpf.workhub.repositories.user.UserRepository;

@Service
public class AuthService {

    private static final String DEFAULT_ROLE = "USER";

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (this.userRepository.existsByEmail(request.email()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");

        if (this.userRepository.existsByUsername(request.username()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already registered");

        var now = LocalDateTime.now();

        var savedUser = this.userRepository.save(User.builder()
                .email(request.email()).username(request.username()).firstName(request.firstName())
                .lastName(request.lastName()).passwordHash(this.passwordEncoder.encode(request.password()))
                .role(DEFAULT_ROLE).createdAt(now).updatedAt(now).contractedResources(List.of(PlanResourceType.DEMANDS))
                .build());

        return responseFor(savedUser);
    }

    public AuthResponseDTO login(LoginRequestDTO request) {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        var user = this.userRepository.findByEmail(request.email()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        return responseFor(user);
    }

    private AuthResponseDTO responseFor(User user) {
        var token = this.jwtService.generateToken(user.getEmail(), user.getRole());
        var response = new UserResponseDTO(user.getId(), user.getEmail(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getRole());

        return new AuthResponseDTO(token, response);
    }
}
