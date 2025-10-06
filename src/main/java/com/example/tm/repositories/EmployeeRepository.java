package com.example.tm.repositories;

import com.example.tm.domain.Employee;
import com.example.tm.domain.OauthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
