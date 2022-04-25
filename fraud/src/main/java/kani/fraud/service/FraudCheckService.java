package kani.fraud.service;

import kani.fraud.model.FraudCheckHistory;
import kani.fraud.repository.FraudCheckHistoryRepository;
import kani.fraud.tools.FraudResponse;
import kani.fraud.tools.NotificationRequest;
import kani.fraud.tools.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;


@Service
public class FraudCheckService {

    private final FraudCheckHistoryRepository fraudCheckHistoryRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public FraudCheckService(FraudCheckHistoryRepository fraudCheckHistoryRepository, RestTemplate restTemplate) {
        this.fraudCheckHistoryRepository = fraudCheckHistoryRepository;
        this.restTemplate = restTemplate;
    }

    public FraudResponse  isFraud(Long customerId) {
        FraudCheckHistory fraudCheckHistory = FraudCheckHistory.builder().customerId(customerId)
                .createdAt(LocalDateTime.now()).
                isFraudster(false).build();
        fraudCheckHistoryRepository.save(fraudCheckHistory);

        NotificationRequest registration = NotificationRequest.builder().
                toCustomerId(customerId)
                .message("Registration new Fraud")
                .type(NotificationType.FRAUD.name())
                .build();
        restTemplate.postForEntity("http://NOTIFICATION/api/notif", registration, String.class);
        return new FraudResponse(false);

    }
}
