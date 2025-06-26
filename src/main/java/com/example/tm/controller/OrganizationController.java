package com.example.tm.controller;

import com.example.tm.domain.Organization;
import com.example.tm.model.OrganizationDto;
import com.example.tm.model.OrganizationMapper;
import com.example.tm.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("organizations")
public class OrganizationController {

    private final OrganizationService organizationService;
    private final OrganizationMapper organizationMapper;

    @GetMapping("id")
    ResponseEntity<?> getById(@PathVariable long id) {
        OrganizationDto dto = organizationService.find(id).map(organizationMapper::toDto).orElseThrow();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody OrganizationDto request) {
        Organization organization = organizationService.create(request);
        OrganizationDto dto = organizationMapper.toDto(organization);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("id")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody OrganizationDto request) {
        Organization organization = organizationService.find(id).orElseThrow();
        OrganizationDto dto = Optional.of(organizationService.update(request, organization))
                .map(organizationMapper::toDto).orElse(null);
        return ResponseEntity.ok(dto);
    }
}
