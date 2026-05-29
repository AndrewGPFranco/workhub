package com.agpf.workhub.auth;

public record AuthResponse(String token, UserResponse user) {
}
