package com.agpf.workhub.rest.user;

import com.agpf.workhub.dtos.http.ResponseAPI;
import com.agpf.workhub.dtos.user.RegisterDailyDTO;
import com.agpf.workhub.services.user.DailyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class UserController {

    private final DailyService dailyService;

    @PostMapping(value = "/daily/register")
    public ResponseEntity<ResponseAPI> register(@Valid @RequestBody RegisterDailyDTO dto,
                                                @AuthenticationPrincipal(expression = "username") String email) {
        return ResponseEntity.ok().body(new ResponseAPI(HttpStatus.CREATED.value(), dailyService.registerDaily(dto, email)));
    }

    @GetMapping(value = "/daily/by-user")
    ResponseEntity<ResponseAPI> getByUser(@RequestParam LocalDate dateFeedback,
                                          @AuthenticationPrincipal(expression = "username") String email) {
        var response = dailyService.getByUser(dateFeedback, email);
        return ResponseEntity.ok(new ResponseAPI(HttpStatus.OK.value(), response));
    }

}
