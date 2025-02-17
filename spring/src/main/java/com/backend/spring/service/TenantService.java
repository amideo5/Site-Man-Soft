package com.backend.spring.service;

import com.backend.spring.models.TenantEntity;

import java.util.List;
import java.util.Optional;

public interface TenantService {

    /**
     * Creates a new tenant.
     *
     * @param tenantEntity the TenantEntity containing tenant details
     * @return the created TenantEntity
     */
    TenantEntity createTenant(TenantEntity tenantEntity);

    /**
     * Updates an existing tenant.
     *
     * @param tenantId the ID of the tenant to be updated
     * @param updatedTenant the updated TenantEntity with new values
     * @return the updated TenantEntity
     */
    TenantEntity updateTenant(Long tenantId, TenantEntity updatedTenant);

    /**
     * Retrieves a tenant by its ID.
     *
     * @param tenantId the ID of the tenant
     * @return an Optional containing the TenantEntity if found, otherwise empty
     */
    Optional<TenantEntity> getTenantById(Long tenantId);

    /**
     * Retrieves all tenants.
     *
     * @return a list of all TenantEntities
     */
    List<TenantEntity> getAllTenants();

    /**
     * Deletes a tenant by its ID.
     *
     * @param tenantId the ID of the tenant to be deleted
     */
    void deleteTenant(Long tenantId);
}
