package com.example.demo.dto;

import com.example.demo.entity.ApplicationStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateApplicationStatusDTO {
    
    @NotNull(message = "Application ID cannot be null")
    private ApplicationStatus status;

    public UpdateApplicationStatusDTO() {} 

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}
