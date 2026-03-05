package com.vaidhyaa.fraud.controller;

import com.vaidhyaa.fraud.dto.FraudReportRequest;
import com.vaidhyaa.fraud.dto.FraudReportResponse;
import com.vaidhyaa.fraud.entity.FraudReport;
import com.vaidhyaa.fraud.service.FraudReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/public/fraud")
@Validated
public class FraudReportController {

    private final FraudReportService service;

    public FraudReportController(FraudReportService service) {
        this.service = service;
    }

    @PostMapping("/report")
    public ResponseEntity<FraudReportResponse> create(@Valid @RequestBody FraudReportRequest req) {
        FraudReport saved = service.createReport(req);
        return ResponseEntity.ok(new FraudReportResponse(true, "Fraud report created", saved.getId()));
    }
}