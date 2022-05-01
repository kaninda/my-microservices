package kani.amqp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class RabbitMqMessageProducer {

    @Autowired
    private final AmqpTemplate amqpTemplate;

    public void publish(Object payload, String exchange, String routingKey) {
        log.info("Publishing message to {}, using routingkey {}, Payload {}",exchange,routingKey,payload);
        amqpTemplate.convertAndSend(exchange, routingKey, payload);
        log.info("Publising sent to message to {}, using routingkey {}, Payload {}",exchange,routingKey,payload);
    }

}
