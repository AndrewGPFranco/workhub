package com.agpf.workhub.rest.user;

import com.agpf.workhub.annotations.PlanResource;
import com.agpf.workhub.dtos.http.ResponseAPI;
import com.agpf.workhub.dtos.user.daily.RegisterDailyDTO;
import com.agpf.workhub.dtos.user.feedback.RegisterFeedbackDTO;
import com.agpf.workhub.enums.plan.PlanResourceType;
import com.agpf.workhub.models.user.User;
import com.agpf.workhub.services.user.DailyService;
import com.agpf.workhub.services.user.FeedbackService;
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
    private final FeedbackService feedbackService;

    @PostMapping(value = "/daily/register")
    @PlanResource(verify = PlanResourceType.DAILY)
    public ResponseEntity<ResponseAPI> registerDaily(@Valid @RequestBody RegisterDailyDTO dto,
                                                     @AuthenticationPrincipal User user) {
        return ResponseEntity.ok().body(new ResponseAPI(HttpStatus.CREATED.value(), dailyService.registerDaily(dto, user)));
    }

    @GetMapping(value = "/daily/by-user")
    @PlanResource(verify = PlanResourceType.DAILY)
    ResponseEntity<ResponseAPI> getDailyByUser(@RequestParam("startDate") LocalDate startDate,
                                               @RequestParam("endDate") LocalDate endDate,
                                               @AuthenticationPrincipal User user) {
        var response = dailyService.getByUser(startDate, endDate, user);
        return ResponseEntity.ok(new ResponseAPI(HttpStatus.OK.value(), response));
    }

    @PostMapping(value = "/feedback/register")
    @PlanResource(verify = PlanResourceType.FEEDBACK)
    public ResponseEntity<ResponseAPI> registerFeedback(@Valid @RequestBody RegisterFeedbackDTO dto,
                                                        @AuthenticationPrincipal User user) {
        return ResponseEntity.ok().body(new ResponseAPI(HttpStatus.CREATED.value(), feedbackService.registerFeedback(dto, user)));
    }

    @GetMapping(value = "/feedback/by-user")
    @PlanResource(verify = PlanResourceType.FEEDBACK)
    ResponseEntity<ResponseAPI> getFeedbacksByUser(@AuthenticationPrincipal User user) {
        var response = feedbackService.getByUser(user);
        return ResponseEntity.ok(new ResponseAPI(HttpStatus.OK.value(), response));
    }

}
