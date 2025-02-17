package com.backend.spring.controller;

import com.backend.spring.models.TenantEntity;
import com.backend.spring.service.TenantService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    private final TenantService tenantService;

    @Autowired
    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping
    public ResponseEntity<TenantEntity> createTenant(@RequestBody TenantEntity tenantEntity, HttpServletRequest request, HttpServletResponse response) {
        TenantEntity createdTenant = tenantService.createTenant(tenantEntity);
        return new ResponseEntity<>(createdTenant, HttpStatus.CREATED);
    }

    @PutMapping("/{tenantId}")
    public ResponseEntity<TenantEntity> updateTenant(@PathVariable Long tenantId, @RequestBody TenantEntity updatedTenant, HttpServletRequest request, HttpServletResponse response) {
        TenantEntity updatedTenantEntity = tenantService.updateTenant(tenantId, updatedTenant);
        return new ResponseEntity<>(updatedTenantEntity, HttpStatus.OK);
    }

    @GetMapping("/{tenantId}")
    public ResponseEntity<TenantEntity> getTenantById(@PathVariable Long tenantId, HttpServletRequest request, HttpServletResponse response) {
        Optional<TenantEntity> tenant = tenantService.getTenantById(tenantId);
        return tenant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<TenantEntity>> getAllTenants(HttpServletRequest request, HttpServletResponse response) {
        List<TenantEntity> tenants = tenantService.getAllTenants();
        return new ResponseEntity<>(tenants, HttpStatus.OK);
    }

    @DeleteMapping("/{tenantId}")
    public ResponseEntity<Void> deleteTenant(@PathVariable Long tenantId, HttpServletRequest request, HttpServletResponse response) {
        tenantService.deleteTenant(tenantId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
