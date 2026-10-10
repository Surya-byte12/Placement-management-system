package com.example.demo.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.ApplicationResponseDTO;
import com.example.demo.dto.UpdateApplicationStatusDTO;
import com.example.demo.entity.Application;
import com.example.demo.entity.ApplicationStatus;
import com.example.demo.entity.User;
import com.example.demo.repository.ApplicationRepository;
import com.example.demo.repository.UserRepository;

@Service
public class RecruiterApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    public RecruiterApplicationService(
            ApplicationRepository applicationRepository,
            UserRepository userRepository) {

        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
    }

    public List<ApplicationResponseDTO> getApplicants(
            String email) {

        User recruiter = findRecruiter(email);

        List<Application> applications =
                applicationRepository
                        .findByJob_Company_Recruiter_Id(
                                recruiter.getId());

        return applications.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public ApplicationResponseDTO updateStatus(
            Long applicationId,
            UpdateApplicationStatusDTO request,
            String email) {

        User recruiter = findRecruiter(email);

        Application application = applicationRepository
                .findById(applicationId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Application not found"));

        Long ownerId = application.getJob()
                .getCompany()
                .getRecruiter()
                .getId();

        if (!ownerId.equals(recruiter.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot manage this application");
        }

        ApplicationStatus newStatus = request.getStatus();

        if (newStatus == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Application status is required");
        }

        if (newStatus == ApplicationStatus.APPLIED) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Recruiters cannot set status to APPLIED");
        }

        application.setStatus(newStatus);

        Application saved =
                applicationRepository.save(application);

        return mapToResponse(saved);
    }

    private User findRecruiter(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Recruiter not found"));
    }

    private ApplicationResponseDTO mapToResponse(
            Application application) {

        return new ApplicationResponseDTO(
                application.getId(),
                application.getJob().getId(),
                application.getJob().getTitle(),
                application.getJob().getCompany().getName(),
                application.getStatus().name()
        );
    }
}  