package com.backend.spring.service;

import com.backend.spring.models.TenantEntity;
import com.backend.spring.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;

    @Autowired
    public TenantServiceImpl(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    public TenantEntity createTenant(TenantEntity tenantEntity) {
        return tenantRepository.save(tenantEntity);
    }

    @Override
    public TenantEntity updateTenant(Long tenantId, TenantEntity updatedTenant) {
        Optional<TenantEntity> existingTenantOptional = tenantRepository.findById(tenantId);
        if (existingTenantOptional.isPresent()) {
            TenantEntity existingTenant = existingTenantOptional.get();
            existingTenant.setName(updatedTenant.getName());
            existingTenant.setDomain(updatedTenant.getDomain());
            return tenantRepository.save(existingTenant);
        } else {
            // Handle the case when the tenant doesn't exist
            throw new RuntimeException("Tenant not found for id: " + tenantId);
        }
    }

    @Override
    public Optional<TenantEntity> getTenantById(Long tenantId) {
        return tenantRepository.findById(tenantId);
    }

    @Override
    public List<TenantEntity> getAllTenants() {
        return (List<TenantEntity>) tenantRepository.findAll();
    }

    @Override
    public void deleteTenant(Long tenantId) {
        tenantRepository.deleteById(tenantId);
    }
}
