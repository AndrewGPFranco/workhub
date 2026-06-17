package com.agpf.workhub.rest.subdomains;

import com.agpf.workhub.annotations.PlanResource;
import com.agpf.workhub.dtos.http.ResponseAPI;
import com.agpf.workhub.dtos.subdomains.RegisterSubdomainDTO;
import com.agpf.workhub.models.user.User;
import com.agpf.workhub.services.subdomains.SubdomainService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.agpf.workhub.enums.plan.PlanResourceType.SUBDOMAINS;

@RestController
@RequiredArgsConstructor
@PlanResource(verify = SUBDOMAINS)
@RequestMapping(value = "/subdomains")
public class SubdomainController {

    private final SubdomainService subdomainService;

    @PostMapping(value = "/register")
    public ResponseEntity<ResponseAPI> register(@RequestBody @Valid RegisterSubdomainDTO dto,
                                                @AuthenticationPrincipal User user) {
        subdomainService.register(dto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseAPI(HttpStatus.CREATED.value(), String.format("Subdomínio %s registrado com sucesso!", dto.name()))
        );
    }

    @GetMapping(value = "/by-user")
    public ResponseEntity<ResponseAPI> subdomainsByUser(@AuthenticationPrincipal User user) {
        var subdomains = subdomainService.subdomainsByUser(user);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseAPI(HttpStatus.OK.value(), subdomains));
    }

}
