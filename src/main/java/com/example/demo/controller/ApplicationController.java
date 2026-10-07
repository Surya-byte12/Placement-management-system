package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApplicationRequestDTO;
import com.example.demo.dto.ApplicationResponseDTO;
import com.example.demo.service.ApplicationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(
            ApplicationService applicationService) {

        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<ApplicationResponseDTO> apply(
            @Valid @RequestBody ApplicationRequestDTO request,
            Authentication authentication) {

        String email = authentication.getName();

        ApplicationResponseDTO response =
                applicationService.apply(request, email);

        return ResponseEntity
                .status(201)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ApplicationResponseDTO>>
            getMyApplications(
                    Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                applicationService
                        .getMyApplications(email)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponseDTO>
            getMyApplication(
                    @PathVariable Long id,
                    Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                applicationService
                        .getMyApplication(id, email)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        applicationService
                .deleteApplication(id, email);

        return ResponseEntity.noContent().build();
    }
}