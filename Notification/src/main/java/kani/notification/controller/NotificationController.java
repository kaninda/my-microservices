package kani.notification.controller;

import kani.notification.Tools.NotificationRegistrationRequest;
import kani.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    @PostMapping(value = "notif", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> manageNotif(@RequestBody NotificationRegistrationRequest notificationRegistrationRequest){
        notificationService.saveNotif(notificationRegistrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
