package com.example.backend.config.token;

import com.example.backend.service.TokenService;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class TokenFilterConfiguerer extends SecurityConfigurerAdapter {

    private final TokenService tokenService;

    public TokenFilterConfiguerer(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public void configure(HttpSecurity httpSecurity) throws Exception {
        TokenFilter tokenFilter = new TokenFilter(tokenService);
        httpSecurity.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
