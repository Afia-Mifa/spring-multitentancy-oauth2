package com.example.tm;

import com.example.tm.domain.AppUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("test")
public class TestController {

    @PersistenceContext
    private EntityManager em;

    @GetMapping
    public ResponseEntity<?> get() {
        AppUser appUser = em.find(AppUser.class, 1);
        return ResponseEntity.ok().build();
    }
}
