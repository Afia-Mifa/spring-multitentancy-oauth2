package com.example.tm.controller;

import com.example.tm.context.ActiveContextHolder;
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

    @GetMapping("app-status")
    public ResponseEntity<?> get() {
        return ResponseEntity.ok().build();
    }
}
