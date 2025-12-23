package com.saas.payment.service;

import com.saas.payment.entity.Payment;
import com.saas.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public Payment processPayment(Long tenantId, BigDecimal amount) {
        // Mock Payment Logic
        boolean isSuccess = Math.random() > 0.1; // 90% success rate

        Payment payment = Payment.builder()
                .tenantId(tenantId)
                .amount(amount)
                .currency("USD")
                .status(isSuccess ? "SUCCESS" : "FAILED")
                .transactionId(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now())
                .build();

        return paymentRepository.save(payment);
    }
}
