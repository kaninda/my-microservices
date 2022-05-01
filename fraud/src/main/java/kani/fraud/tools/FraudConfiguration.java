package kani.fraud.tools;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class FraudConfiguration {

    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchanges;

    @Value("${rabbitmq.queue.fraud}")
    private String fraudQueue;

    @Value("{rabbitmq.routing-key.internal-notification}")
    private String internalNotificationRoutingKey;

    public String getInternalExchanges() {
        return internalExchanges;
    }

    public String getFraudQueue() {
        return fraudQueue;
    }

    public String getInternalNotificationRoutingKey() {
        return internalNotificationRoutingKey;
    }

    @Bean
    public Exchange exchange() {
        return new TopicExchange(getInternalExchanges());
    }

    @Bean
    public Queue queue() {
        return new Queue(getFraudQueue());
    }

    @Bean
    public Binding internalToNotificationBinding (){
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(getInternalNotificationRoutingKey())
                .noargs();
    }
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
