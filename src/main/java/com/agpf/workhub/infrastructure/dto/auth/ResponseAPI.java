package com.agpf.workhub.infrastructure.dto.auth;

/**
 * Record genérica para padronizar envio de respostas ao frontend.
 *
 * @param response
 * @author andrewgo
 */
public record ResponseAPI<T>(T response) {}
