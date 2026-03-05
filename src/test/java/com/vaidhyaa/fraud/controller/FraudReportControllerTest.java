package com.vaidhyaa.fraud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaidhyaa.fraud.dto.FraudReportRequest;
import com.vaidhyaa.fraud.entity.FraudReport;
import com.vaidhyaa.fraud.exception.DuplicateTransactionException;
import com.vaidhyaa.fraud.exception.GlobalExceptionHandler;
import com.vaidhyaa.fraud.service.FraudReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FraudReportController.class)
@Import(GlobalExceptionHandler.class) // important: loads your @RestControllerAdvice into test
class FraudReportControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockBean FraudReportService service;

    private static final String URL = "/api/v1/public/fraud/report";

    @Test
    void create_shouldReturn200_whenValidRequest() throws Exception {
        FraudReportRequest req = new FraudReportRequest();
        req.setName("Mani");
        req.setEmail("mani@gmail.com");
        req.setType("PAYMENT_FRAUD");
        req.setDescription("Suspicious transaction");
        req.setTransactionId("1234567890123456"); // must be 16 digits

        FraudReport saved = new FraudReport();
        saved.setId(1L);

        given(service.createReport(any(FraudReportRequest.class))).willReturn(saved);

        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Fraud report created"))
                .andExpect(jsonPath("$.reportId").value(1));
    }

    @Test
    void create_shouldReturn400_whenValidationFails() throws Exception {
        FraudReportRequest badReq = new FraudReportRequest();
        badReq.setName("Mani");
        badReq.setEmail("mani@gmail.com");
        badReq.setType("PAYMENT_FRAUD");
        badReq.setDescription("Suspicious transaction");
        badReq.setTransactionId("123"); // invalid (not 16 digits)

        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badReq)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                // message depends on your DTO annotation message:
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void create_shouldReturn409_whenDuplicateTransaction() throws Exception {
        FraudReportRequest req = new FraudReportRequest();
        req.setName("Mani");
        req.setEmail("mani@gmail.com");
        req.setType("PAYMENT_FRAUD");
        req.setDescription("Suspicious transaction");
        req.setTransactionId("1234567890123456");

        willThrow(new DuplicateTransactionException("Transaction already reported"))
                .given(service).createReport(any(FraudReportRequest.class));

        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.error").value("Conflict"))
                .andExpect(jsonPath("$.message").value("Transaction already reported"));
    }

    @Test
    void create_shouldReturn415_whenContentTypeIsTextPlain() throws Exception {
        mockMvc.perform(post(URL)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("hello"))
                .andExpect(status().isUnsupportedMediaType());
    }
}