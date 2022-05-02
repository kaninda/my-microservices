package kani.notification.Tools;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfiguration {

    @Value("${rabbitmq.queues.customer}")
    private String customerQueue;

    @Value("${rabbitmq.queues.fraud}")
    private String fraudQueue;

    @Bean
    public Queue customerQueue() {
        return new Queue(customerQueue);
    }

    @Bean
    public Queue fraudQueue() {
        return new Queue(fraudQueue);
    }
}


