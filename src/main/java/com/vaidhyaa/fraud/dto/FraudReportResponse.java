package com.vaidhyaa.fraud.dto;

public class FraudReportResponse {
    private boolean success;
    private String message;
    private Long reportId;

    public FraudReportResponse() {}

    public FraudReportResponse(boolean success, String message, Long reportId) {
        this.success = success;
        this.message = message;
        this.reportId = reportId;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Long getReportId() { return reportId; }
    public void setReportId(Long reportId) { this.reportId = reportId; }
}