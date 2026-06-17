package com.agpf.workhub.dtos.subdomains;

import com.agpf.workhub.models.subdomains.Subdomain;
import com.agpf.workhub.models.user.User;
import jakarta.validation.constraints.NotBlank;

public record RegisterSubdomainDTO(
        String urlPhoto,
        @NotBlank(message = "É necessário informar um nome ao subdomínio.") String name
) {

    public static Subdomain toEntity(RegisterSubdomainDTO dto, User user) {
        return Subdomain.builder().user(user).name(dto.name).urlPhoto(dto.urlPhoto).build();
    }

}
