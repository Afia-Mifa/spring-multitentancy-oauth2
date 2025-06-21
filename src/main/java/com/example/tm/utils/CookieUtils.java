package com.example.tm.utils;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.WebUtils;
import org.yaml.snakeyaml.util.UriEncoder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Objects;

public class CookieUtils {

    private static final String COOKIE_ENCODING_METHOD = "UTF-8";

    public static void addCookie(HttpServletResponse response, String name, String value, int cookieAge) {
        try {
            value = URLEncoder.encode(value, COOKIE_ENCODING_METHOD);
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Error while encoding cookie value", ex);
        }
        String setCookieHeader = String.format(
                "%s=%s; Max-Age=%d; Path=/; SameSite=None; Secure",
                name, value, cookieAge
        );
        response.addHeader(HttpHeaders.SET_COOKIE, setCookieHeader);
    }

    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);

        if (Objects.isNull(cookie)) {
            return null;
        }

        String encodedVal = cookie.getValue();
        try {
            return URLDecoder.decode(encodedVal, COOKIE_ENCODING_METHOD);
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Error while decoding cookie value", ex);
        }
    }
}
