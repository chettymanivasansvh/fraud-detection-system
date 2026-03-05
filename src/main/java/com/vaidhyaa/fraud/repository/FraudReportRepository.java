package com.vaidhyaa.fraud.repository;

import com.vaidhyaa.fraud.entity.FraudReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FraudReportRepository extends JpaRepository<FraudReport, Long> {
    Optional<FraudReport> findByTransactionId(String transactionId);
}