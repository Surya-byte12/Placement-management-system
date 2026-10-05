package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                .requestMatchers("/api/auth/**")
                .permitAll()

                .requestMatchers(
                        HttpMethod.GET,
                        "/api/jobs",
                        "/api/jobs/**"
                )
                .permitAll()

                .requestMatchers(
                        HttpMethod.POST,
                        "/api/jobs"
                )
                .hasRole("RECRUITER")

                .requestMatchers(
                        HttpMethod.PUT,
                        "/api/jobs/**"
                )
                .hasRole("RECRUITER")

                .requestMatchers(
                        HttpMethod.DELETE,
                        "/api/jobs/**"
                )
                .hasRole("RECRUITER")

                .requestMatchers("/api/applications/**")
                .hasRole("USER")

                .requestMatchers("/api/recruiter/**")
                .hasRole("RECRUITER")

                .requestMatchers("/api/admin/**")
                .hasRole("ADMIN")

                .anyRequest()
                .authenticated()
            )

            .addFilterBefore(
                    jwtAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}