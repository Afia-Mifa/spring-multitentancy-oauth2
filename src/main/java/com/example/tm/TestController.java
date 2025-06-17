package com.example.tm;

import com.example.tm.domain.AppUser;
import com.example.tm.repositories.AppUserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

    private final JdbcTemplate jdbcTemplate;

    @GetMapping
    public ResponseEntity<?> get() {
        String sql = "SELECT COUNT(*) FROM app_user";
        Integer i = jdbcTemplate.queryForObject(sql, Integer.class);
        return ResponseEntity.ok().build();
    }
}
