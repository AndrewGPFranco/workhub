package com.agpf.workhub.rest.demands;

import com.agpf.workhub.annotations.PlanResource;
import com.agpf.workhub.dtos.demands.EditDemandDTO;
import com.agpf.workhub.dtos.demands.InputObservationDTO;
import com.agpf.workhub.dtos.demands.RegisterDemandDTO;
import com.agpf.workhub.dtos.http.ResponseAPI;
import com.agpf.workhub.enums.demands.PriorityDemandType;
import com.agpf.workhub.enums.demands.SprintType;
import com.agpf.workhub.enums.demands.StatusDemandType;
import com.agpf.workhub.enums.plan.PlanResourceType;
import com.agpf.workhub.models.user.User;
import com.agpf.workhub.services.demands.DemandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/demands")
@PlanResource(verify = PlanResourceType.DEMANDS)
public class DemandController {

    private final DemandService demandService;

    @PostMapping(value = "/register")
    ResponseEntity<ResponseAPI> register(@Valid @RequestBody RegisterDemandDTO dto, @AuthenticationPrincipal User user) {
        var response = demandService.createDemand(dto, user);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(new ResponseAPI(HttpStatus.CREATED.value(), response));
    }

    @PatchMapping(value = "/add-observations")
    ResponseEntity<ResponseAPI> addObservationsToDemand(@Valid @RequestBody InputObservationDTO dto) {
        demandService.addObservationsToDemand(dto);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(
                new ResponseAPI(HttpStatus.CREATED.value(), "Observações adicionadas!")
        );
    }

    @GetMapping(value = "/by-user")
    ResponseEntity<ResponseAPI> getByUser(@RequestParam int page,
                                          @RequestParam(required = false) UUID subdomainId,
                                          @RequestParam(required = false) StatusDemandType status,
                                          @RequestParam(required = false) PriorityDemandType priority,
                                          @RequestParam(required = false) SprintType sprint,
                                          @AuthenticationPrincipal User user) {
        var response = demandService.getByUser(page, user, status, priority, sprint, subdomainId);
        return ResponseEntity.ok(new ResponseAPI(HttpStatus.OK.value(), response));
    }

    @PatchMapping(value = "/edit/{id}")
    ResponseEntity<ResponseAPI> editDemand(@PathVariable(name = "id") UUID idDemand,
                                           @RequestBody EditDemandDTO dto,
                                           @AuthenticationPrincipal User user) {
        demandService.editDemand(idDemand, dto, user);
        return ResponseEntity.ok(new ResponseAPI(HttpStatus.OK.value(), "Demanda editada com sucesso!"));
    }

    @DeleteMapping(value = "/delete/{id}")
    ResponseEntity<ResponseAPI> deleteDemand(@PathVariable(name = "id") UUID idDemand,
                                             @AuthenticationPrincipal User user) {
        demandService.deleteDemand(idDemand, user);
        return ResponseEntity.ok(new ResponseAPI(HttpStatus.OK.value(), "Demanda deletada com sucesso!"));
    }

    @GetMapping(value = "/search")
    ResponseEntity<ResponseAPI> searchDemand(@RequestParam String title,
                                             @RequestParam(required = false) UUID subdomainId,
                                             @AuthenticationPrincipal User user) {
        var response = demandService.searchByDemand(title, user, subdomainId);
        return ResponseEntity.ok(new ResponseAPI(HttpStatus.OK.value(), response));
    }

    @PutMapping(value = "/alter-subdomain/{idDemand}")
    ResponseEntity<ResponseAPI> changeDemandSubdomain(@PathVariable UUID idDemand,
                                                      @RequestParam UUID idSubdomain,
                                                      @AuthenticationPrincipal User user) {
        var response = demandService.changeDemandSubdomain(idDemand, idSubdomain, user);
        return ResponseEntity.ok(new ResponseAPI(HttpStatus.OK.value(), response));
    }

}
