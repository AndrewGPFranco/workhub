package com.agpf.workhub.rest.demands;

import com.agpf.workhub.dtos.demands.RegisterDemandDTO;
import com.agpf.workhub.models.auth.User;
import com.agpf.workhub.services.demands.DemandService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demands")
public class DemandController {

    private final DemandService demandService;

    public DemandController(DemandService demandService) {
        this.demandService = demandService;
    }

    @PostMapping(value = "/register")
    ResponseEntity<String> register(@Valid @RequestBody RegisterDemandDTO dto, @AuthenticationPrincipal User user) {
        String response = demandService.createDemand(dto, user);
        return ResponseEntity.ok().body(response);
    }

}
