package com.example.backend.config.token;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backend.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TokenFilter extends GenericFilterBean {

    private final TokenService tokenService;

    public TokenFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String auth = request.getHeader("Authorization");
        if (ObjectUtils.isEmpty(auth)) {
            filterChain.doFilter(servletRequest, servletResponse);
        }

        if (!auth.startsWith("Bearer")) {
            filterChain.doFilter(servletRequest, servletResponse);
        }

        String token = auth.substring(7);
        DecodedJWT decodedJWT = tokenService.verify(token);
        if (decodedJWT == null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String principal = decodedJWT.getClaim("principal").asString();
        String role = decodedJWT.getClaim("role").asString();

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));

        // Create the authentication token
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(principal, "(protected)", authorities);

        // Get the current security context
        SecurityContext securityContext = SecurityContextHolder.getContext();

        // Set the authentication token in the security context
        securityContext.setAuthentication(authenticationToken);
        filterChain.doFilter(servletRequest, servletResponse); // ***

//        UsernamePasswordAuthenticationToken AuthenticationToken= new UsernamePasswordAuthenticationToken(principal,"(protected)",authorities);
//
//       SecurityContextHolder securityContext = (SecurityContextHolder) SecurityContextHolder.getContext();
//       securityContext.setContext((SecurityContext) AuthenticationToken);

    }

}
