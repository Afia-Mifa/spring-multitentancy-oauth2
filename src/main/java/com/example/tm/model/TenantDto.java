package com.example.tm.model;

import java.util.List;

public record TenantDto(long id, String name, String tenantId, String dbUrl, String dbUser, String dbPassword,
                        List<TenantPermissionDto> permissions) {
}