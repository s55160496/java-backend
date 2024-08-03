package com.example.backend.config;

import com.example.backend.config.token.TokenFilter;
import com.example.backend.config.token.TokenFilterConfiguerer;
import com.example.backend.service.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration  {

private final TokenService tokenService;

    public SecurityConfiguration(TokenService tokenService) {
        this.tokenService = tokenService;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    private final String[] PUBLIC = {
            "/actuator/**",
            "/user/register",
            "/user/login",

    };


//    public SecurityConfig(TokenService tokenService) {
//        this.tokenService = tokenService;
//    }


//    @Bean
//    public SecurityFilterChain configure (HttpSecurity http) throws Exception {
//       return
//               http.cors(config -> {
//                    CorsConfiguration cors = new CorsConfiguration();
//                    cors.setAllowCredentials(true);
//                    cors.setAllowedOriginPatterns(Collections.singletonList("http://*"));
//                    cors.addAllowedHeader("*");
//                    cors.addAllowedMethod("GET");
//                    cors.addAllowedMethod("POST");
//                    cors.addAllowedMethod("PUT");
//                    cors.addAllowedMethod("DELETE");
//                    cors.addAllowedMethod("OPTIONS");
//
//                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//                    source.registerCorsConfiguration("/**", cors);
//
//                    config.configurationSource(source);
//                }).csrf(csrf -> csrf.disable())
//                .authorizeRequests().requestMatchers(PUBLIC).anonymous()
//                .anyRequest().authenticated()
//                 .and().apply(tokenService, UsernamePasswordAuthenticationFilter.class)
//                  .build();
//       //.and().apply(new TokenFilterConfiguerer(tokenService))
//    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
         http.cors(config -> {
                    CorsConfiguration cors = new CorsConfiguration();
                    cors.setAllowCredentials(true);
                    cors.setAllowedOriginPatterns(Collections.singletonList("http://*"));
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
                .authorizeRequests(authorize -> authorize
                        .requestMatchers(PUBLIC).anonymous() // Ensure PUBLIC is defined correctly
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new TokenFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
        return  http.build();
    }


}