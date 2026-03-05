package com.vaidhyaa.fraud.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class FraudReportRequest {

    @NotBlank(message = "name is required")
    @Size(min = 2, max = 50, message = "name must be 2 to 50 characters")
    private String name;

    @NotBlank(message = "email is required")
    @Email(message = "email must be valid")
    private String email;

    @NotBlank(message = "type is required")
    private String type; // billing, doctor, lab, etc.

    @NotBlank(message = "description is required")
    @Size(min = 5, max = 500, message = "description must be 5 to 500 characters")
    private String description;

    // Interview: 16 digit validation
    @NotBlank(message = "transactionId is required")
    @Pattern(regexp = "^[0-9]{16}$", message = "transactionId must be exactly 16 digits")
    private String transactionId;

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
}