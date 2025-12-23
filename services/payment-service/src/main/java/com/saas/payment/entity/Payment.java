package com.saas.payment.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tenantId;
    private BigDecimal amount;
    private String currency;
    private String status; // SUCCESS, FAILED, PENDING
    private String transactionId;
    private LocalDateTime timestamp;
}
