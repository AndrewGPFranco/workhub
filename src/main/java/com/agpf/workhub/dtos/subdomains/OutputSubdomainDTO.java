package com.agpf.workhub.dtos.subdomains;

import com.agpf.workhub.models.subdomains.Subdomain;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OutputSubdomainDTO(
        String urlPhoto,
        @NotNull UUID id,
        @NotBlank String name
) {

    public static OutputSubdomainDTO fromEntity(Subdomain subdomain) {
        return new OutputSubdomainDTO(subdomain.getUrlPhoto(), subdomain.getId(), subdomain.getName());
    }

}
