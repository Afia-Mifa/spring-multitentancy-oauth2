package com.example.tm.service;

import com.example.tm.domain.Feature;
import com.example.tm.domain.Organization;
import com.example.tm.domain.Tenant;
import com.example.tm.domain.TenantPermission;
import com.example.tm.model.OrganizationDto;
import com.example.tm.model.TenantDto;
import com.example.tm.model.TenantPermissionDto;
import com.example.tm.repositories.FeatureRepository;
import com.example.tm.repositories.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final FeatureRepository featureRepository;

    public Optional<Organization> find(long id) {
        return organizationRepository.findById(id);
    }

    @Transactional
    public Organization create(OrganizationDto dto) {
        return save(dto, new Organization());
    }

    @Transactional
    public Organization update(OrganizationDto dto, Organization organization) {
        return save(dto, organization);
    }

    private Organization save(OrganizationDto dto, Organization organization) {
        return organizationRepository.save(prepare(dto, organization));
    }

    private Organization prepare(OrganizationDto dto, Organization organization) {
        organization.setName(dto.name());
        organization.setCode(dto.code());
        organization.setDomain(dto.domain());
        organization.setTenant(prepareTenant(dto.tenant(), organization));
        return organization;
    }

    private Tenant prepareTenant(TenantDto dto, Organization organization) {
        Tenant tenant = new Tenant();
        tenant.setName(dto.name());
        tenant.setTenantId(dto.tenantId());
        tenant.setDbUrl(dto.dbUrl());
        tenant.setDbUser(dto.dbUser());
        tenant.setDbPassword(dto.dbPassword());

        List<TenantPermission> permissions = dto.permissions().stream().map(d -> {
            List<Feature> features = featureRepository.findByIdIn(d.getFeatures());
            TenantPermission permission = new TenantPermission();
            permission.setRole(d.getRole());
            permission.getFeatures().addAll(features);
            permission.setTenant(tenant);
            return permission;
        }).toList();

        tenant.setPermissions(permissions);
        return tenant;
    }
}
