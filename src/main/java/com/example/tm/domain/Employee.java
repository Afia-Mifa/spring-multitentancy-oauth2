package com.example.tm.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Employee extends BaseEntity {

    @Id
    private String id;

    private String name;

    @OneToOne
    private Organization organization;
}
