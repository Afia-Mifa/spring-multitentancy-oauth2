package com.example.tm.filter;

import com.example.tm.context.ActiveContextHolder;
import com.example.tm.domain.Employee;
import com.example.tm.model.AuthenticatedUser;
import com.example.tm.repositories.EmployeeRepository;
import com.example.tm.system.config.ClientDatabase;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ApplicationContextFilter extends OncePerRequestFilter {
    public static final String JWT_CLAIM_USERNAME = "sub";

    private final EmployeeRepository employeeRepository;

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain) throws ServletException, IOException {

        boolean contextInThreadLocal = false;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getPrincipal() instanceof Jwt jwt) {
                AuthenticatedUser authenticatedUser = build(jwt);
                ActiveContextHolder.setContext(authenticatedUser);
                contextInThreadLocal = true;
            }
            filterChain.doFilter(request, response);
        } finally {
            if (contextInThreadLocal) {
                ActiveContextHolder.remove();
            }
        }
    }

    private AuthenticatedUser build(Jwt jwt) {
        String employeeId = jwt.getClaimAsString(JWT_CLAIM_USERNAME);
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();

        return new AuthenticatedUser(
                employee.getId(),
                employee.getName(),
                employee.getOrganization().getId(),
                ClientDatabase.valueOf(employee.getOrganization().getTenant().getTenantId()),
                Map.of("role", List.of("root"))
        );
    }
}
