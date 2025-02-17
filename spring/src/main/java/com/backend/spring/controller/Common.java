package com.backend.spring.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class Common {

    @GetMapping("/getCsrfToken")
    public Map<String, String> getCsrfToken(HttpServletRequest request) {
        // Retrieve the CSRF token from the request
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        // Return it in the response body
        return Map.of("csrfToken", csrfToken.getToken());
    }
}
