package com.example.tm.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.TenantId;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tenant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String tenantId;

    private String name;

    private String dbUrl;

    private String dbUser;

    private String dbPassword;

    @OneToOne
    private Organization organization;
}
