package com.saas.billing_service.controller;

import com.saas.billing_service.entity.Plan;
import com.saas.billing_service.entity.Subscription;
import com.saas.billing_service.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/billing")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SubscriptionController {

    private final BillingService billingService;

    @GetMapping("/plans")
    public ResponseEntity<List<Plan>> getAllPlans() {
        return ResponseEntity.ok(billingService.getAllPlans());
    }

    @GetMapping("/subscriptions/{tenantId}")
    public ResponseEntity<List<Subscription>> getSubscriptions(@PathVariable Long tenantId) {
        return ResponseEntity.ok(billingService.getSubscriptionsByTenant(tenantId));
    }

    @PostMapping("/subscribe")
    public ResponseEntity<Subscription> subscribe(@RequestParam Long tenantId, @RequestParam String planName) {
        return ResponseEntity.ok(billingService.createSubscription(tenantId, planName));
    }
}
