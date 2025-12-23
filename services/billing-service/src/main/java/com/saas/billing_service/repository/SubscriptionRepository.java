package com.saas.billing_service.repository;

import com.saas.billing_service.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository
        extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByTenantId(Long tenantId);
}
