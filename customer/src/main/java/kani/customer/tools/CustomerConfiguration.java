package kani.customer.tools;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CustomerConfiguration {

    @Bean
    public RestTemplate buildREstTemplate () {
        return new RestTemplate();
    }
}
