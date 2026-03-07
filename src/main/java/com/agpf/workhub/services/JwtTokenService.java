package com.agpf.workhub.services;

import com.agpf.workhub.entities.User;
import com.agpf.workhub.enums.RoleType;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtTokenService {

    @Value("${reader.jwt.secret}")
    private String secret;

    /**
     * Responsável por gerar o token.
     *
     * @param user que está tentando realizar o login.
     * @return token.
     */
    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("workhub")
                    .withClaim("id", user.getId().toString())
                    .withClaim("role", user.getRoles().stream().map(Enum::name).toList())
                    .withClaim("isAdmin", user.getRoles().contains(RoleType.ADMIN))
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new JWTCreationException("Erro ao criar o token", e);
        }
    }

    /**
     * Verifica se o token está válido, tanto em questão de expiração, quanto se foi gerado pela aplicação.
     *
     * @param token a ser válidado.
     */
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("workhub")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Erro ao verificar o token", e);
        }
    }

    /**
     * Defini a expiração do token para 2 horas após sua criação.
     *
     * @return Instant da expiração do token.
     */
    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-03:00"));
    }
}