package kani.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication(scanBasePackages = {
        "kani.amqp",
        "kani.customer"
})
@EnableEurekaClient
@PropertySources(value = {
        @PropertySource("classpath:client-${spring.profiles.active}.properties"),
})
public class CustomerApplication {
    public static  void main (String args []){
        SpringApplication.run(CustomerApplication.class, args);
    }
}
