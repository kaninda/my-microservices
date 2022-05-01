package kani.fraud.service;

import kani.amqp.module.RabbitMqMessageProducer;
import kani.fraud.model.FraudCheckHistory;
import kani.fraud.repository.FraudCheckHistoryRepository;
import kani.fraud.tools.FraudConfiguration;
import kani.fraud.tools.FraudResponse;
import kani.fraud.tools.NotificationRequest;
import kani.fraud.tools.NotificationType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;

@Service
@Slf4j
public class FraudCheckService {

    private final FraudCheckHistoryRepository fraudCheckHistoryRepository;
    private final RestTemplate restTemplate;
    private final RabbitMqMessageProducer rabbitMqMessageProducer;
    private final FraudConfiguration fraudConfiguration;

    @Autowired
    public FraudCheckService(
            FraudCheckHistoryRepository fraudCheckHistoryRepository,
            RestTemplate restTemplate,
            RabbitMqMessageProducer rabbitMqMessageProducer, FraudConfiguration fraudConfiguration) {
        this.fraudCheckHistoryRepository = fraudCheckHistoryRepository;
        this.restTemplate = restTemplate;
        this.rabbitMqMessageProducer = rabbitMqMessageProducer;
        this.fraudConfiguration = fraudConfiguration;
    }

    public FraudResponse  isFraud(Long customerId) {
        buidFraudByCustomerId(customerId);
        NotificationRequest registration = NotificationRequest.builder().
                toCustomerId(customerId)
                .message("Registration new Fraud")
                .type(NotificationType.FRAUD.name())
                .build();
         rabbitMqMessageProducer.publish(
                 registration,
                 fraudConfiguration.getInternalExchanges(),
                 fraudConfiguration.getInternalNotificationRoutingKey()
         );
        return new FraudResponse(false);
    }

    private void buidFraudByCustomerId(Long customerId) {
        FraudCheckHistory fraudCheckHistory = FraudCheckHistory.builder().customerId(customerId)
                .createdAt(LocalDateTime.now()).
                isFraudster(false).build();
        fraudCheckHistoryRepository.save(fraudCheckHistory);
    }
}
