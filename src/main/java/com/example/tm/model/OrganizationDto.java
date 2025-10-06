package com.example.tm.model;

public record OrganizationDto(long id, String name, String code, String domain, TenantDto tenant) {
}