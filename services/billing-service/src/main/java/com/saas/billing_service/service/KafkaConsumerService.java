package com.saas.billing_service.service;

import com.saas.billing_service.entity.Plan;
import com.saas.billing_service.entity.Subscription;
import com.saas.billing_service.repository.PlanRepository;
import com.saas.billing_service.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final SubscriptionRepository subscriptionRepository;
    private final PlanRepository planRepository;

    @KafkaListener(topics = "tenant-created", groupId = "billing-group")
    public void listen(String tenantIdStr) {
        System.out.println("Received Tenant ID in Billing Service: " + tenantIdStr);
        Long tenantId = Long.valueOf(tenantIdStr);

        // check if Free plan exists or create default mechanism is handled by
        // DataSeeder,
        // but we can ensure fail-safe here.
        Plan freePlan = planRepository.findByName("FREE").orElseGet(() -> {
            // Fallback if DataSeeder hasn't run or FREE plan missing
            return planRepository.save(Plan.builder()
                    .name("FREE")
                    .description("Default Free Plan")
                    .price(BigDecimal.ZERO)
                    .features("Basic")
                    .build());
        });

        // Create default subscription if not exists
        if (subscriptionRepository.findByTenantId(tenantId).isEmpty()) {
            Subscription subscription = Subscription.builder()
                    .tenantId(tenantId)
                    .plan(freePlan)
                    .startDate(LocalDate.now())
                    .nextBillingDate(LocalDate.now().plusMonths(1))
                    .status(Subscription.SubscriptionStatus.ACTIVE)
                    .build();

            subscriptionRepository.save(subscription);
            System.out.println("Created default subscription for tenant: " + tenantId);
        }
    }
}
