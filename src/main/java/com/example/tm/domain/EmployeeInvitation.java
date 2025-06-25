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
public class EmployeeInvitation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Employee invitor;

    @ManyToOne
    private Employee invitee;

    private int validity;

    private Date expiresOn;

    @Enumerated(EnumType.STRING)
    private InvitationStatus invitationStatus;
}
