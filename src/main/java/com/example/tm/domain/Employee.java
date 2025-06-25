package com.example.tm.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Employee extends BaseEntity {

    @Id
    private String id;

    private String name;

    private Date dob;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToOne
    private Organization organization;

    @Enumerated(EnumType.STRING)
    private Status status;
}
