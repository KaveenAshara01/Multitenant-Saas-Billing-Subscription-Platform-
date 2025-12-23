package com.saas.payment.controller;

import com.saas.payment.entity.Payment;
import com.saas.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<Payment> processPayment(@RequestParam Long tenantId, @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(paymentService.processPayment(tenantId, amount));
    }
}
