package com.example.tm.model;

import com.example.tm.domain.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrganizationMapper.class})
public interface EmployeeMapper {
    @Mapping(target = "role", ignore = true)
    EmployeeDto toDto(Employee employee);
}
