package com.example.tm.service;

import com.example.tm.domain.Organization;
import com.example.tm.domain.Tenant;
import com.example.tm.model.OrganizationDto;
import com.example.tm.repositories.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

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

    private static Organization prepare(OrganizationDto dto, Organization organization) {
        organization.setName(dto.name());
        organization.setCode(dto.code());
        organization.setDomain(dto.domain());
        Tenant tenant = new Tenant();
        tenant.setName(dto.tenant().name());
        tenant.setTenantId(dto.tenant().tenantId());
        tenant.setDbUrl(dto.tenant().dbUrl());
        tenant.setDbUser(dto.tenant().dbUser());
        tenant.setDbPassword(dto.tenant().dbPassword());
        tenant.setOrganization(organization);
        organization.setTenant(tenant);
        return organization;
    }
}
