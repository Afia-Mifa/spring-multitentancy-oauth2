package com.example.tm.controller;

import com.example.tm.domain.Employee;
import com.example.tm.model.EmployeeDto;
import com.example.tm.model.EmployeeMapper;
import com.example.tm.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    private final EmployeeMapper employeeMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody EmployeeDto dto) {
        Employee employee = employeeService.create(dto);
        return ResponseEntity.ok(employeeMapper.toDto(employee));
    }
}
