package com.agpf.workhub.rest.notifications;

import com.agpf.workhub.dtos.http.ResponseAPI;
import com.agpf.workhub.models.user.User;
import com.agpf.workhub.services.notifications.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping(value = "/by-user")
    ResponseEntity<ResponseAPI> getNotificationsUser(@AuthenticationPrincipal User user) {
        var notifications = notificationService.getNotificationsUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseAPI(HttpStatus.OK.value(), notifications));
    }

    @PutMapping(value = "/mark-as-read")
    void markNotificationAsRead(@AuthenticationPrincipal User user, @RequestParam Long idNotification) {
        notificationService.markNotificationAsRead(user.getId(), idNotification);
    }

    @PutMapping(value = "/mark-all-as-read")
    void markAllNotificationAsRead(@AuthenticationPrincipal User user) {
        notificationService.markAllNotificationAsRead(user.getId());
    }

}
