package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ApplicationResponseDTO;
import com.example.demo.dto.UpdateApplicationStatusDTO;
import com.example.demo.service.RecruiterApplicationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/recruiter/applications")
public class RecruiterApplicationController {

    private final RecruiterApplicationService recruiterApplicationService;

    public RecruiterApplicationController(
            RecruiterApplicationService recruiterApplicationService) {

        this.recruiterApplicationService =
                recruiterApplicationService;
    }

    @GetMapping
    public ResponseEntity<List<ApplicationResponseDTO>>
            getApplicants(Authentication authentication) {

        String email = authentication.getName();

        List<ApplicationResponseDTO> applicants =
                recruiterApplicationService.getApplicants(email);

        return ResponseEntity.ok(applicants);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApplicationResponseDTO> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateApplicationStatusDTO request,
            Authentication authentication) {

        String email = authentication.getName();

        ApplicationResponseDTO updated =
                recruiterApplicationService.updateStatus(
                        id, request, email);

        return ResponseEntity.ok(updated);
    }
}