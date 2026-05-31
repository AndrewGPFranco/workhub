package com.agpf.workhub.dtos.http;

/**
 * DTO responsável por retornar respostas padronizadas da API.
 *
 * @param httpStatusCode
 * @param data
 */
public record ResponseAPI(
        int httpStatusCode,
        Object data
) {
}
