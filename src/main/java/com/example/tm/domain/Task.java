package com.example.tm.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Task extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String taskId;

    private String title;

    private String description;

    private Date startDate;

    private Date endDate;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;
}
