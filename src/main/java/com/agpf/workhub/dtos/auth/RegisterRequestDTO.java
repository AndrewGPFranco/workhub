package com.agpf.workhub.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(@Email @NotBlank String email, @NotBlank @Size(max = 30) String username, @NotBlank @Size(max = 40) String firstName,
                @NotBlank @Size(max = 40) String lastName, @NotBlank @Size(min = 8, max = 100) String password) {
}
