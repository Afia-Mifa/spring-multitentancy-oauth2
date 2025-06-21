package com.example.tm.domain;

import com.example.tm.utils.DateUtils;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "oauth_token")
public class OauthToken extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(length = 4000)
    private String accessToken;

    @Column(length = 4000)
    private String refreshToken;

    @Column(length = 4000)
    private String authorizationCode;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date accessTokenExpiresAt;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date refreshTokenExpiresAt;

    public boolean isRefreshTokenExpired() {
        return DateUtils.getNow().after(this.getRefreshTokenExpiresAt());
    }

    public boolean isAccessTokenExpired() {
        return DateUtils.getNow().after(this.getAccessTokenExpiresAt());
    }
}