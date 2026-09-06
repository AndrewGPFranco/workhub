package com.agpf.workhub.rest.admin;

import com.agpf.workhub.dtos.demands.OutputDemandCronDTO;
import com.agpf.workhub.services.demands.DemandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/admin")
public class AdminController {

    private final DemandService demandService;

    @GetMapping(value = "/all-demands")
    ResponseEntity<List<OutputDemandCronDTO>> obterTodasDemandas(@RequestParam String title) {
        return ResponseEntity.ok().body(demandService.obterTodasDemandas(title));
    }

}
