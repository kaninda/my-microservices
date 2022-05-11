package kani.customer.service;

import kani.amqp.RabbitMqMessageProducer;
import kani.customer.model.Customer;
import kani.customer.repository.CustomerRepository;
import kani.customer.tools.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
public class CustomerService {

    private final RestTemplate restTemplate;
    private final CustomerRepository customerRepository;
    private final RabbitMqMessageProducer rabbitMqMessageProducer;
    private final CustomerConfiguration customerConfiguration;
    @Value("${client.fraud.url}")
    private String fraudUrl;

    @Autowired
    public CustomerService(
            RestTemplate restTemplate,
            CustomerRepository customerRepository,
            RabbitMqMessageProducer rabbitMqMessageProducer,
            CustomerConfiguration customerConfiguration) {
        this.restTemplate = restTemplate;
        this.customerRepository = customerRepository;
        this.rabbitMqMessageProducer = rabbitMqMessageProducer;
        this.customerConfiguration = customerConfiguration;
    }

    public void add(CustomerRegistrationRequest customerRegistrationRequest) {
        final Customer customerSaved = registerCustomer(customerRegistrationRequest);
        sentToFraudService(customerSaved);
        NotificationRequest registration = NotificationRequest.builder().
                toCustomerId(customerSaved.getCustomerId())
                .message("Registration new Customer")
                .type(NotificationType.CUSTOMER.name())
                .build();
        rabbitMqMessageProducer.publish(
                registration,
                customerConfiguration.getInternalExchanges(),
                customerConfiguration.getInternalNotificationRoutingKey()
        );
    }

    private void sentToFraudService(Customer customerSaved) {
        final FraudResponse fraudResponse = restTemplate.
                getForObject(
                        fraudUrl.concat("/api/fraud/{customerId}"),
                        FraudResponse.class,
                        customerSaved.getCustomerId()
                );
        if (fraudResponse.getFraud()) {
            throw new IllegalArgumentException("It's Fraud");
        }
    }

    private Customer registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        log.info("Saving the customer {}", customerRegistrationRequest.getName());
        Customer customer = Customer.builder().mail(customerRegistrationRequest.getLogin()).
                name(customerRegistrationRequest.getName()).
                password(customerRegistrationRequest.getPassword()).build();
        final Customer customerSaved = customerRepository.saveAndFlush(customer);
        return customerSaved;
    }
}
