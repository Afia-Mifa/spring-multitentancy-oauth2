package com.example.tm.model;

import com.example.tm.domain.Organization;
import com.example.tm.domain.Tenant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TenantMapper {
    TenantDto toDto(Tenant tenant);
}
