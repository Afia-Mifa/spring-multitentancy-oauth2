package com.example.tm.system.config;

import com.example.tm.filter.CookieTokenAuthenticationFilter;
import com.example.tm.filter.ApplicationContextFilter;
import com.example.tm.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Component
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final EmployeeRepository employeeRepository;
    private static final String[] WHITE_LIST_URLS = {
            "/swagger-ui/**",
            "/password-reset/**",
            "/auth/login",
            "/auth/refresh",
            "/auth/callback",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers(WHITE_LIST_URLS)
                        .permitAll().anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .addFilterBefore(new CookieTokenAuthenticationFilter(), BearerTokenAuthenticationFilter.class)
                .addFilterAfter(new ApplicationContextFilter(employeeRepository), BearerTokenAuthenticationFilter.class)
                .build();
    }
}
