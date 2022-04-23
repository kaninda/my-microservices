package kani.customer.service;

import kani.customer.model.Customer;
import kani.customer.repository.CustomerRepository;
import kani.customer.tools.CustomerRegistrationRequest;
import kani.customer.tools.FraudResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
public class CustomerService {

    private final RestTemplate restTemplate;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(RestTemplate restTemplate, CustomerRepository customerRepository) {
        this.restTemplate = restTemplate;
        this.customerRepository = customerRepository;
    }

    public void add (CustomerRegistrationRequest customerRegistrationRequest) {
        log.info("Saving the customer {}", customerRegistrationRequest.getName());
        Customer customer = Customer.builder().mail(customerRegistrationRequest.getLogin()).
                name(customerRegistrationRequest.getName()).
                password(customerRegistrationRequest.getPassword()).build();
         final Customer customerSaved = customerRepository.saveAndFlush(customer);
        final FraudResponse fraudResponse = restTemplate.
                getForObject("http://FRAUD/api/fraud-check/{customerId}", FraudResponse.class, customerSaved.getId());
        if( fraudResponse.getFraud()){
            throw new IllegalArgumentException("It's Fraud");
        }
    }
}
