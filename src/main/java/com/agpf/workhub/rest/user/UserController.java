package com.agpf.workhub.rest.user;

import com.agpf.workhub.dtos.http.ResponseAPI;
import com.agpf.workhub.dtos.user.daily.RegisterDailyDTO;
import com.agpf.workhub.dtos.user.feedback.RegisterFeedbackDTO;
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
    private static final String USERNAME = "username";

    @PostMapping(value = "/daily/register")
    public ResponseEntity<ResponseAPI> registerDaily(@Valid @RequestBody RegisterDailyDTO dto,
                                                     @AuthenticationPrincipal(expression = USERNAME) String email) {
        return ResponseEntity.ok().body(new ResponseAPI(HttpStatus.CREATED.value(), dailyService.registerDaily(dto, email)));
    }

    @GetMapping(value = "/daily/by-user")
    ResponseEntity<ResponseAPI> getDailyByUser(@RequestParam LocalDate dateFeedback,
                                               @AuthenticationPrincipal(expression = USERNAME) String email) {
        var response = dailyService.getByUser(dateFeedback, email);
        return ResponseEntity.ok(new ResponseAPI(HttpStatus.OK.value(), response));
    }

    @PostMapping(value = "/feedback/register")
    public ResponseEntity<ResponseAPI> registerFeedback(@Valid @RequestBody RegisterFeedbackDTO dto,
                                                        @AuthenticationPrincipal(expression = USERNAME) String email) {
        return ResponseEntity.ok().body(new ResponseAPI(HttpStatus.CREATED.value(), feedbackService.registerFeedback(dto, email)));
    }

    @GetMapping(value = "/feedback/by-user")
    ResponseEntity<ResponseAPI> getFeedbacksByUser(@AuthenticationPrincipal(expression = USERNAME) String email) {
        var response = feedbackService.getByUser(email);
        return ResponseEntity.ok(new ResponseAPI(HttpStatus.OK.value(), response));
    }

}
