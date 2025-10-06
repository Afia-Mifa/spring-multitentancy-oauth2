package com.example.tm.model;

import com.example.tm.system.config.ClientDatabase;

import java.util.List;
import java.util.Map;

public record AuthenticatedUser(
        String employeeId,
        String name,
        Long organizationId,
        ClientDatabase clientDatabase,
        Map<String, List<String>> roles
) {
}
