package kani.notification.service;

import kani.notification.Tools.NotificationRegistrationRequest;
import kani.notification.model.Notification;
import kani.notification.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    public void saveNotif (NotificationRegistrationRequest notificationRegistrationRequest){
        log.info(
                "Registration on nofification {} for the user {}",
                notificationRegistrationRequest.getType(),
                notificationRegistrationRequest.getToCustomerId()
        );
        Notification notification = buildNotification(notificationRegistrationRequest);
        notificationRepository.save(notification);
    }

    private Notification buildNotification (NotificationRegistrationRequest notificationRegistrationRequest){
        return Notification.builder().typeNotification(notificationRegistrationRequest.getType())
                .customerId(notificationRegistrationRequest.getToCustomerId())
                .message(notificationRegistrationRequest.getMessage())
                .sentAt(LocalDateTime.now()).build();
    }

}
