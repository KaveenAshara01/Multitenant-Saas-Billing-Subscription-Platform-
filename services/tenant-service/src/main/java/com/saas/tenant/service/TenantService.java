package com.saas.tenant.service;

import com.saas.tenant.dto.TenantDto;
import com.saas.tenant.entity.Tenant;
import com.saas.tenant.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TenantService {
    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "tenant-created";

    public Tenant createTenant(TenantDto tenantDto) {
        Tenant tenant = new Tenant();
        tenant.setName(tenantDto.getName());
        tenant.setEmail(tenantDto.getEmail());
        tenant.setStatus("ACTIVE");

        Tenant savedTenant = tenantRepository.save(tenant);

        // Publish event to Kafka
        kafkaTemplate.send(TOPIC, savedTenant.getId().toString());

        return savedTenant;
    }

    public Tenant getTenantById(Long id) {
        return tenantRepository.findById(id).orElseThrow(() -> new RuntimeException("Tenant not found"));
    }
}
