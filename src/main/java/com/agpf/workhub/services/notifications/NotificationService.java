package com.agpf.workhub.services.notifications;

import com.agpf.workhub.clients.NotificationClient;
import com.agpf.workhub.dtos.notifications.OutputNotificationDTO;
import com.agpf.workhub.dtos.notifications.UpdateNotificationDTO;
import com.agpf.workhub.models.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/notifications")
public class NotificationService {

    private final NotificationClient notificationClient;

    public List<OutputNotificationDTO> getNotificationsUser(User user) {
        return notificationClient.getNotifications(user.getId());
    }

    public void markNotificationAsRead(Long idUser, Long idNotification) {
        notificationClient.markNotificationAsRead(new UpdateNotificationDTO(idUser, idNotification));
    }

    public void markAllNotificationAsRead(Long idUser) {
        notificationClient.markAllNotificationAsRead(idUser);
    }
}
