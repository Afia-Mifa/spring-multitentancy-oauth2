package com.example.tm;

import com.example.tm.domain.Employee;
import com.example.tm.domain.OauthToken;
import com.example.tm.repositories.EmployeeRepository;
import com.example.tm.repositories.OauthTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import static com.example.tm.utils.Constants.REFRESH_TOKEN_EXPIRES_AFTER_DAYS;
import static com.example.tm.utils.DateUtils.convertToDate;
import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class OauthService {

    @Value("${oauth2.client-id}")
    private String clientId;

    @Value("${oauth2.client-secret}")
    private String clientSecret;

    @Value("${oauth2.logout-uri}")
    private String logoutUri;

    @Value("${oauth2.redirect-uri}")
    private String redirectUri;

    @Value("${oauth2.token-uri}")
    private String tokenUri;

    @Value("${oauth2.jwks-uri}")
    private String jwksUri;

    private final OauthTokenRepository tokenRepository;

    private final EmployeeRepository employeeRepository;

    private final RestTemplate restTemplate;

    public OauthToken exchangeAuthorizationCodeForTokens(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("redirect_uri", redirectUri);

        Map<String, Object> responseBody = sendTokenRequest(body, headers);
        OauthToken oauthToken = OauthToken.builder()
                .authorizationCode(code)
                .build();

        setOauthTokenData(responseBody, oauthToken);

        return tokenRepository.save(oauthToken);
    }

    public OauthToken exchangeRefreshTokenForAccessToken(String refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "refresh_token");
        body.add("refresh_token", refreshToken);

        Map<String, Object> responseBody = sendTokenRequest(body, headers);
        OauthToken oauthToken = OauthToken.builder()
                .authorizationCode(refreshToken)
                .build();

        setOauthTokenData(responseBody, oauthToken);

        return tokenRepository.save(oauthToken);
    }

    private Map<String, Object> sendTokenRequest(MultiValueMap<String, String> body, HttpHeaders headers) {
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                tokenUri, HttpMethod.POST, request, new ParameterizedTypeReference<>() {
                }
        );

        Map<String, Object> responseBody = response.getBody();
        if (responseBody == null || !responseBody.containsKey("access_token")) {
            throw new RuntimeException("Failed to retrieve tokens");
        }

        return responseBody;
    }

    private void setOauthTokenData(Map<String, Object> responseBody, OauthToken tokenEntity) {
        LocalDateTime now = now();
        String accessToken = (String) responseBody.get("access_token");
        Date accessTokenExpiresAt = convertToDate(now.plusSeconds(((Number) responseBody.get("expires_in")).longValue()));
        Date refreshTokenExpiresAt = convertToDate(now.plusDays(REFRESH_TOKEN_EXPIRES_AFTER_DAYS));
        Employee employee = employeeRepository.findById(getUserNameFromAccessToken(accessToken))
                .orElseThrow(() -> new AccessDeniedException("Access Denied"));

        tokenEntity.setEmployee(employee);
        tokenEntity.setAccessToken(accessToken);
        tokenEntity.setRefreshToken(((String) responseBody.get("refresh_token")));
        tokenEntity.setAccessTokenExpiresAt(accessTokenExpiresAt);
        tokenEntity.setRefreshTokenExpiresAt(refreshTokenExpiresAt);
    }

    private String getUserNameFromAccessToken(String accessToken) {
        JwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwksUri).build();
        Jwt jwt = jwtDecoder.decode(accessToken);
        return jwt.getSubject();
    }
}