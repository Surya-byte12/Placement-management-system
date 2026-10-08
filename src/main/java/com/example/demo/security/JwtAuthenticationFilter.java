package com.example.demo.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserRepository userRepository) {

        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader =
                request.getHeader("Authorization");

        System.out.println("=================================");
        System.out.println("REQUEST = " + request.getMethod()
                + " " + request.getRequestURI());
        System.out.println("AUTH HEADER PRESENT = "
                + (authorizationHeader != null));

        // No JWT
        if (authorizationHeader == null
                || !authorizationHeader.startsWith("Bearer ")) {

            System.out.println("NO JWT TOKEN");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(7);

        // Invalid JWT
        if (!jwtService.isTokenValid(token)) {

            System.out.println("JWT TOKEN IS INVALID");

            filterChain.doFilter(request, response);
            return;
        }

        String email = jwtService.extractEmail(token);

        System.out.println("JWT EMAIL = " + email);

        User user =
                userRepository.findByEmail(email).orElse(null);

        // User not found
        if (user == null) {

            System.out.println("USER NOT FOUND IN DATABASE");

            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("USER = " + user.getEmail());
        System.out.println("ROLE = " + user.getRole());

        if (SecurityContextHolder.getContext()
                .getAuthentication() == null) {

            var authorities = List.of(
                    new SimpleGrantedAuthority(
                            "ROLE_" + user.getRole().name()
                    )
            );

            var authentication =
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            null,
                            authorities
                    );

            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

            System.out.println(
                    "AUTHENTICATION CREATED"
            );

            System.out.println(
                    "AUTHORITIES = "
                    + authentication.getAuthorities()
            );
        }

        System.out.println(
                "FINAL AUTH = "
                + SecurityContextHolder.getContext()
                    .getAuthentication()
        );

        System.out.println("=================================");

        filterChain.doFilter(request, response);
    }
}