package kani.notification;

import kani.amqp.module.RabbitMqMessageProducer;
import kani.notification.Tools.NotificationConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(
        scanBasePackages = {
                "kani.amqp",
                "kani.notification"
        }
)
@EnableEurekaClient
public class NotificationApplication {

    public static void main (String [] args ){
        SpringApplication.run(NotificationApplication.class);
    }

    @Bean
    CommandLineRunner commandLineRunner(RabbitMqMessageProducer rabbitMqMessageProducer,
                                        NotificationConfiguration notificationConfiguration){
      return args -> {
         rabbitMqMessageProducer.publish(
                 new Person("Arnaud",21),
                 notificationConfiguration.getInternalExchanges(),
                 notificationConfiguration.getInternalNotificationRoutingKey()
         );
      };
    }

    record Person (String name, int age){}
}
