package com.example.backend.config;

import com.example.backend.config.token.TokenFilter;
import com.example.backend.service.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final TokenService tokenService;
    private final String[] PUBLIC = {
            "/actuator/**",
            "/user/register",
            "/user/login",
            "/socket/**",
            "/user/activate",
            "/user/resend-activation-email",
           // "/chat/**", //TODO : remove from config (Security Reason)
    };


    public SecurityConfiguration(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.cors(config -> {
                    CorsConfiguration cors = new CorsConfiguration();
                    cors.setAllowCredentials(true);
                    cors.setAllowedOriginPatterns(Collections.singletonList("http://localhost*"));
                    cors.addAllowedHeader("*");
                    cors.addAllowedMethod("GET");
                    cors.addAllowedMethod("POST");
                    cors.addAllowedMethod("PUT");
                    cors.addAllowedMethod("DELETE");
                    cors.addAllowedMethod("OPTIONS");

                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", cors);

                    config.configurationSource(source);
                }).csrf(csrf -> csrf.disable())
                .authorizeRequests(
                        authorize -> authorize
                                //.anyRequest().permitAll()
                        .requestMatchers(PUBLIC).anonymous() // Ensure PUBLIC is defined correctly
                       .anyRequest().authenticated()
                )
                .addFilterBefore(new TokenFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}