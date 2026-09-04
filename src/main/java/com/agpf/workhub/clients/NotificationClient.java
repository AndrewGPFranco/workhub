package com.agpf.workhub.clients;

import com.agpf.workhub.dtos.notifications.OutputNotificationDTO;
import com.agpf.workhub.dtos.notifications.UpdateNotificationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "notifications",
        url = "${services.notifications.url}"
)
public interface NotificationClient {

    @GetMapping(value = "/api/v1/recover-notifications/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<OutputNotificationDTO> getNotifications(@PathVariable Long id);

    @PutMapping(value = "/api/v1/update-notification")
    void markNotificationAsRead(@RequestBody UpdateNotificationDTO dto);

    @PutMapping(value = "/api/v1/update-all-notifications/{id}")
    void markAllNotificationAsRead(@PathVariable Long id);

}
