package com.example.tm.controller;

import com.example.tm.OauthService;
import com.example.tm.domain.OauthToken;
import com.example.tm.utils.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.example.tm.utils.Constants.*;
import static com.example.tm.utils.CookieUtils.addCookie;
import static com.example.tm.utils.DateUtils.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private final OauthService oauthService;

    @GetMapping("callback")
    public void callback(@RequestParam("code") String code, HttpServletResponse response) {
        OauthToken oauthToken = oauthService.exchangeAuthorizationCodeForTokens(code);
        setAuthCookiesAndHeader(response, oauthToken);
    }

    @GetMapping("refresh")
    public void refresh(HttpServletRequest request, HttpServletResponse response) {
        String token = CookieUtils.getCookieValue(request, REFRESH_TOKEN);
        OauthToken oauthToken = oauthService.exchangeRefreshTokenForAccessToken(token);
        setAuthCookiesAndHeader(response, oauthToken);
    }

    private static void setAuthCookiesAndHeader(HttpServletResponse response, OauthToken token) {
        int refreshTokenAge = REFRESH_TOKEN_EXPIRES_AFTER_DAYS * HOURS_IN_DAY * SECONDS_IN_HOUR;
        int accessTokenAge = ACCESS_TOKEN_EXPIRES_AFTER_MIN * SECONDS_IN_MINUTE;
        addCookie(response, ACCESS_TOKEN, token.getAccessToken(), accessTokenAge);
        addCookie(response, REFRESH_TOKEN, token.getRefreshToken(), refreshTokenAge);
    }
}