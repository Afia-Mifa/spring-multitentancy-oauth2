package com.example.tm.model;

import com.example.tm.domain.Feature;
import com.example.tm.domain.Role;
import com.example.tm.domain.TenantPermission;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantPermissionDto {
    long id;
    Role role;
    List<Long> features;

    public static TenantPermissionDto from(TenantPermission permission) {
        return TenantPermissionDto.builder()
                .id(permission.getId())
                .role(permission.getRole())
                .features(permission.getFeatures().stream().map(Feature::getId).toList())
                .build();
    }
}
