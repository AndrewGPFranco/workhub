package com.agpf.workhub.rest.demands;

import com.agpf.workhub.dtos.demands.RegisterDemandDTO;
import com.agpf.workhub.dtos.http.ResponseAPI;
import com.agpf.workhub.services.demands.DemandService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    ResponseEntity<ResponseAPI> register(@Valid @RequestBody RegisterDemandDTO dto,
                                         @AuthenticationPrincipal(expression = "username") String email) {
        String response = demandService.createDemand(dto, email);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(new ResponseAPI(HttpStatus.CREATED.value(), response));
    }

}
