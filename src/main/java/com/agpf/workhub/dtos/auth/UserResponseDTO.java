package com.agpf.workhub.dtos.auth;

public record UserResponseDTO(Long id, String email, String username, String firstName, String lastName, String role) {
}
