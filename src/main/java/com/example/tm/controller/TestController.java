package com.example.tm.controller;

import com.example.tm.domain.Employee;
import com.example.tm.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("test")
public class TestController {

//    @PersistenceContext
//    private EntityManager em;
//
//    private final AppUserRepository appUserRepository;

    private final EmployeeRepository employeeRepository;

    private final JdbcTemplate jdbcTemplate;

    @GetMapping
    public ResponseEntity<?> get() {
//        String sql = "SELECT COUNT(*) FROM employee";
//        Integer i = jdbcTemplate.queryForObject(sql, Integer.class);
        Employee employee = employeeRepository.findById("root").orElseThrow();
        return ResponseEntity.ok().build();
    }
}
