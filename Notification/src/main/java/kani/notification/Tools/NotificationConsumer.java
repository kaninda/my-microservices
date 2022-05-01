package kani.notification.Tools;

import kani.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationConsumer {

    private final NotificationService notificationService;

    @Autowired
    public NotificationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = {
            "${rabbitmq.queues.fraud}",
            "${rabbitmq.queues.customer}"
    })
    public void consumer (NotificationRegistrationRequest notificationRegistrationRequest){
        log.info("Saving notification = {}",notificationRegistrationRequest);
        notificationService.saveNotif(notificationRegistrationRequest);
    }
}
