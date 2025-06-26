package com.example.tm.model;

import com.example.tm.domain.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TenantMapper.class})
public interface OrganizationMapper {
    OrganizationDto toDto(Organization organization);
}
