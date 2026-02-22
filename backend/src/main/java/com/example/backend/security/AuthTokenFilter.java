package com.example.backend.security;

import com.example.backend.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {

    public static final String BEARER_ = "Bearer ";
    private final CustomJWTUtils customJWTUtils;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthTokenFilter(CustomJWTUtils customJWTUtils, CustomUserDetailsService customUserDetailsService) {
        this.customJWTUtils = customJWTUtils;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String headerAuth = request.getHeader("Authorization");

            if (headerAuth != null && headerAuth.startsWith(BEARER_)) {
                String token = headerAuth.substring(BEARER_.length());
                log.info("Token received: {}", token);

                boolean valid = customJWTUtils.validateJWTToken(token);
                log.info("Token valid? {}", valid);

                if (valid) {
                    final String username = customJWTUtils.getUserFromToken(token);
                    log.info("Username from token: {}", username);

                    final UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                    log.info("Loaded userDetails: username={}, authorities={}", userDetails.getUsername(), userDetails.getAuthorities());

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource()
                            .buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    log.info("SecurityContext auth after set: {}", SecurityContextHolder.getContext().getAuthentication());
                } else {
                    // TODO: Fix error handling. Throwing an exception here stops the filter chain, and it never reaches JWTAuthenticationEntryPoint.
                    throw new BadCredentialsException("Invalid or expired token :/");
                }
            }
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
        }

        // Needed for the next filter in the SecurityFilterChain.
        filterChain.doFilter(request, response);
    }
}
