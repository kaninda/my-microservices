package kani.customer.controller;

import kani.customer.service.CustomerService;
import kani.customer.tools.CustomerRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(value="customers",
            consumes = MediaType.APPLICATION_JSON_VALUE,
             produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCustomer (@RequestBody CustomerRegistrationRequest customerRegistrationRequest){
      customerService.add(customerRegistrationRequest);
      return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
