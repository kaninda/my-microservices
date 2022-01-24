package kani.fraud.controller;

import kani.fraud.service.FraudService;
import kani.fraud.tools.FraudResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class FraudController {

    private final FraudService fraudService;

    @Autowired
    public FraudController(FraudService fraudService) {
        this.fraudService = fraudService;
    }

    @GetMapping(value ="fraud-check/{customerId}")
    public ResponseEntity<FraudResponse>  checkFraud (@PathVariable Long customerId) {
        log.info("checking the user id with id {}");
        FraudResponse fraudResponse = fraudService.isFraud(customerId);
        return ResponseEntity.ok(fraudResponse);
    }
}
