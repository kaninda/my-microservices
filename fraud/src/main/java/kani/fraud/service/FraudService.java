package kani.fraud.service;

import kani.fraud.tools.FraudResponse;
import org.springframework.stereotype.Service;

@Service
public class FraudService {
    public FraudResponse isFraud ( Long customerId) {
        return new FraudResponse(false);
    }
}
