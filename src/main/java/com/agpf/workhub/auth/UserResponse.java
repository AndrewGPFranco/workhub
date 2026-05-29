package com.agpf.workhub.auth;

public record UserResponse(Long id, String email, String username, String firstName, String lastName, String role) {
}
