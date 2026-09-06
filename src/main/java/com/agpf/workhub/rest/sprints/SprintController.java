package com.agpf.workhub.rest.sprints;

import com.agpf.workhub.annotations.PlanResource;
import com.agpf.workhub.dtos.http.ResponseAPI;
import com.agpf.workhub.dtos.sprints.InputDemandsToSprintDTO;
import com.agpf.workhub.dtos.sprints.InputSprintDTO;
import com.agpf.workhub.enums.plan.PlanResourceType;
import com.agpf.workhub.models.user.User;
import com.agpf.workhub.services.sprints.SprintService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/sprint")
@PlanResource(verify = PlanResourceType.DEMANDS)
public class SprintController {

    private final SprintService sprintService;

    @GetMapping(value = "/by-user")
    ResponseEntity<ResponseAPI> getSprintsByUser(@AuthenticationPrincipal User user, @RequestParam(required = false) UUID idSubdomain) {
        return ResponseEntity.ok().body(new ResponseAPI(HttpStatus.OK.value(),
                sprintService.getSprintsByUserAndSubdomain(user, idSubdomain)));
    }

    @PostMapping
    ResponseEntity<ResponseAPI> register(@AuthenticationPrincipal User user, @RequestBody @Valid InputSprintDTO dto) {
        return ResponseEntity.ok().body(new ResponseAPI(HttpStatus.CREATED.value(), sprintService.register(user, dto)));
    }

    @PostMapping(value = "/add-demands")
    ResponseEntity<ResponseAPI> addDemandsToSprint(@AuthenticationPrincipal User user,
                                                   @RequestBody @Valid InputDemandsToSprintDTO dto) {
        return ResponseEntity.ok().body(new ResponseAPI(HttpStatus.CREATED.value(), sprintService.addDemandsToSprint(user, dto)));
    }

}
