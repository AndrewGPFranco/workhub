package com.agpf.workhub.rest.admin;

import com.agpf.workhub.dtos.demands.OutputDemandCronDTO;
import com.agpf.workhub.services.demands.DemandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/admin")
public class AdminController {

    private final DemandService demandService;

    @GetMapping(value = "/all-demands")
    ResponseEntity<List<OutputDemandCronDTO>> obterTodasDemandas() {
        return ResponseEntity.ok().body(demandService.obterTodasDemandas());
    }

}
