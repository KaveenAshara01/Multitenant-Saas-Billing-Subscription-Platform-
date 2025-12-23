package com.saas.billing_service.service;

import com.saas.billing_service.entity.Plan;
import com.saas.billing_service.entity.Subscription;
import com.saas.billing_service.repository.PlanRepository;
import com.saas.billing_service.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final SubscriptionRepository subscriptionRepository;
    private final PlanRepository planRepository;

    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }

    public List<Subscription> getSubscriptionsByTenant(Long tenantId) {
        return subscriptionRepository.findByTenantId(tenantId);
    }

    @Transactional
    public Subscription createSubscription(Long tenantId, String planName) {
        Plan plan = planRepository.findByName(planName)
                .orElseThrow(() -> new RuntimeException("Plan not found: " + planName));

        // Check if tenant already has an active subscription
        boolean hasActive = subscriptionRepository.findByTenantId(tenantId).stream()
                .anyMatch(s -> s.getStatus() == Subscription.SubscriptionStatus.ACTIVE);

        if (hasActive) {
            throw new RuntimeException("Tenant already has an active subscription");
        }

        Subscription subscription = Subscription.builder()
                .tenantId(tenantId)
                .plan(plan)
                .status(Subscription.SubscriptionStatus.ACTIVE)
                .startDate(LocalDate.now())
                .nextBillingDate(LocalDate.now().plusMonths(1)) // Default to monthly
                .build();

        return subscriptionRepository.save(subscription);
    }
}
