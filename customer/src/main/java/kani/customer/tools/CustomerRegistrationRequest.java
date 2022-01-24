package kani.customer.tools;

import lombok.Data;

@Data
public class CustomerRegistrationRequest {
       private String name;
       private String login;
       private String password;
}
