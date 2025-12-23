package com.saas.billing_service.config;

import com.saas.billing_service.entity.Plan;
import com.saas.billing_service.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final PlanRepository planRepository;

    @Override
    public void run(String... args) throws Exception {
        if (planRepository.count() == 0) {
            planRepository.save(Plan.builder()
                    .name("FREE")
                    .description("Basic free plan")
                    .price(BigDecimal.ZERO)
                    .features("Basic Support, 5 Users")
                    .build());

            planRepository.save(Plan.builder()
                    .name("PRO")
                    .description("Professional plan")
                    .price(new BigDecimal("29.99"))
                    .features("Priority Support, 50 Users, Analytics")
                    .build());

            planRepository.save(Plan.builder()
                    .name("ENTERPRISE")
                    .description("Enterprise grade plan")
                    .price(new BigDecimal("99.99"))
                    .features("24/7 Support, Unlimited Users, Dedicated Manager")
                    .build());
        }
    }
}
