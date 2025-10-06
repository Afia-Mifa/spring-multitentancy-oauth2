package com.example.tm.filter;

import com.example.tm.utils.CookieUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.example.tm.utils.Constants.ACCESS_TOKEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class CookieTokenAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain) throws ServletException, IOException {
        if (request.getHeader(AUTHORIZATION) == null) {
            String token = CookieUtils.getCookieValue(request, ACCESS_TOKEN);
            request = new HttpServletRequestWrapper(request) {
                @Override
                public String getHeader(String name) {
                    if (HttpHeaders.AUTHORIZATION.equalsIgnoreCase(name)
                            && StringUtils.isNotEmpty(token)) {
                        return "Bearer " + token;
                    }
                    return super.getHeader(name);
                }
            };
        }
        filterChain.doFilter(request, response);
    }
}
