package com.example.tm.service;

import com.example.tm.domain.*;
import com.example.tm.model.EmployeeDto;
import com.example.tm.repositories.EmployeeRepository;
import com.example.tm.repositories.OrganizationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final OrganizationRepository organizationRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public Employee create(EmployeeDto dto) {
        Employee employee = new Employee();
        employee.setName(dto.name());
        employee.setEmail(dto.email());
        employee.setPhoneNumber(dto.phoneNumber());

        Organization organization = organizationRepository.findById(dto.id()).orElseThrow();
        employee.setOrganization(organization);

        List<Feature> features = organization.getTenant().getPermissions()
                .stream().filter(p -> p.getRole().equals(dto.role()))
                .map(TenantPermission::getFeatures).flatMap(Collection::stream)
                .toList();

        employee.setFeatures(features);
        return employeeRepository.save(employee);
    }
}
