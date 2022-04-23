package kani.fraud.service;

import kani.fraud.model.FraudCheckHistory;
import kani.fraud.repository.FraudCheckHistoryRepository;
import kani.fraud.tools.FraudResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class FraudCheckService {

    private final FraudCheckHistoryRepository fraudCheckHistoryRepository;

    @Autowired
    public FraudCheckService(FraudCheckHistoryRepository fraudCheckHistoryRepository) {
        this.fraudCheckHistoryRepository = fraudCheckHistoryRepository;
    }

    public FraudResponse  isFraud(Long customerId) {
        FraudCheckHistory fraudCheckHistory = FraudCheckHistory.builder().customerId(customerId)
                .createdAt(LocalDateTime.now()).
                isFraudster(false).build();
        fraudCheckHistoryRepository.save(fraudCheckHistory);
        return new FraudResponse(false);
    }
}
