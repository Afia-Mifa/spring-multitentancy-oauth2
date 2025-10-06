package com.example.tm.model;

import com.example.tm.domain.Role;

public record EmployeeDto(long id, String name, String dob, String phoneNumber, String email, OrganizationDto organization,
                          Role role) {
}
