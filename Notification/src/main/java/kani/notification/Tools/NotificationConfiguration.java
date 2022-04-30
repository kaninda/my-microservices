package kani.notification.Tools;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfiguration {

    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchanges;

    @Value("${rabbitmq.queue.notification}")
    private String notificationQueue;

    @Value("{rabbitmq.routing-key.internal-notification}")
    private String internalNotificationRoutingKey;


    public String getInternalExchanges() {
        return internalExchanges;
    }

    public String getNotificationQueue() {
        return notificationQueue;
    }

    public String getInternalNotificationRoutingKey() {
        return internalNotificationRoutingKey;
    }

    @Bean
    public Exchange exchange() {
        return new TopicExchange(internalExchanges);
    }

    @Bean
    public Queue queue() {
        return new Queue(notificationQueue);
    }

    @Bean
    public Binding internalToNotificationBinding (){
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(getInternalNotificationRoutingKey())
                .noargs();
    }
}


