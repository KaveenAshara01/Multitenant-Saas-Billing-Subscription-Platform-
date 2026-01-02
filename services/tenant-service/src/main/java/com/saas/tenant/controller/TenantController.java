package com.saas.tenant.controller;

import com.saas.tenant.dto.TenantDto;
import com.saas.tenant.entity.Tenant;
import com.saas.tenant.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenants")
@CrossOrigin("*")
public class TenantController {
    @Autowired
    private TenantService tenantService;

    @PostMapping
    public ResponseEntity<Tenant> createTenant(@RequestBody TenantDto tenantDto) {
        return ResponseEntity.ok(tenantService.createTenant(tenantDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tenant> getTenant(@PathVariable Long id) {
        return ResponseEntity.ok(tenantService.getTenantById(id));
    }
}
