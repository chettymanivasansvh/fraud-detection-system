package com.vaidhyaa.fraud.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "fraud_report")
public class FraudReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="reporter_name", nullable = false)
    private String name;

    @Column(name="reporter_email", nullable = false)
    private String email;

    @Column(name="incident_type", nullable = false)
    private String type;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name="transaction_id", nullable = false, length = 16)
    private String transactionId;

    @Column(nullable = false)
    private String status; // NEW, IN_REVIEW, CLOSED

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at", nullable = false)
    private Date createdAt;

    @PrePersist
    public void beforeInsert() {
        this.createdAt = new Date();
        if (this.status == null) this.status = "NEW";
    }

    public Long getId() { return id; }

    public void setId(Long id) {
		this.id = id;
	}

	public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getCreatedAt() { return createdAt; }
}