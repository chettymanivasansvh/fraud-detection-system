package com.vaidhyaa.fraud.service;

import com.vaidhyaa.fraud.dto.FraudReportRequest;
import com.vaidhyaa.fraud.entity.FraudReport;
import com.vaidhyaa.fraud.exception.DuplicateTransactionException;
import com.vaidhyaa.fraud.repository.FraudReportRepository;

import java.util.Optional;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FraudReportService {

    private final FraudReportRepository repo;

    public FraudReportService(FraudReportRepository repo) {
        this.repo = repo;
    }
    @Cacheable(cacheNames = "fraudByTxn", key = "#transactionId", unless = "#result == null || #result.isEmpty()")
    public Optional<FraudReport> findByTransactionId(String transactionId){
		return repo.findByTransactionId(transactionId);
    }

    @Transactional
    @CachePut(cacheNames = "fraudByTxn", key = "#req.transactionId")
    public FraudReport createReport(FraudReportRequest req) {
        findByTransactionId(req.getTransactionId())
                .ifPresent(r -> { throw new DuplicateTransactionException("Transaction already reported"); });

        FraudReport report = new FraudReport();
        report.setName(req.getName());
        report.setEmail(req.getEmail());
        report.setType(req.getType());
        report.setDescription(req.getDescription());
        report.setTransactionId(req.getTransactionId());

        return repo.save(report);
    }
}