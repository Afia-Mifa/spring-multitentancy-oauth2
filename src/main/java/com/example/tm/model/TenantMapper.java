package com.example.tm.model;

import com.example.tm.domain.Organization;
import com.example.tm.domain.Tenant;
import com.example.tm.domain.TenantPermission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TenantMapper {
    @Mapping(target = "permissions", expression = "java(getPermissions(tenant))")
    public abstract TenantDto toDto(Tenant tenant);

    protected List<TenantPermissionDto> getPermissions(Tenant tenant) {
        return tenant.getPermissions().stream().map(TenantPermissionDto::from).toList();
    }
}
