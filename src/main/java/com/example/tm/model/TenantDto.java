package com.example.tm.model;

public record TenantDto(long id, String name, String tenantId, String dbUrl, String dbUser, String dbPassword) {
}